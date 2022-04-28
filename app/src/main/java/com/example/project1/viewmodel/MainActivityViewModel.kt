package com.example.project1.viewmodel

import android.app.Application import androidx.lifecycle.*
import com.example.project1.modelclass.Quote
import com.example.project1.retrofit.QuoteApiService
import com.example.project1.retrofit.retrofit
import com.example.project1.roomDB.DataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import retrofit2.awaitResponse

enum class QuoteApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel constructor(application: Application) : ViewModel() {

    private val _status = MutableLiveData<QuoteApiStatus>()

    val status: LiveData<QuoteApiStatus> = _status

    lateinit var _quote: LiveData<List<Quote>>
     var mainDatabase: DataBase

    var userDataList: MutableLiveData<List<Quote>> = MutableLiveData()

    init {
        mainDatabase = DataBase.getDatabase(application)
        viewModelScope.launch(Dispatchers.IO) {
            fetchData().collect {
                userDataList.postValue(it)


            }
        }


    }

    fun fetchData(): Flow<List<Quote>> = flow {
        val retrofitService: QuoteApiService by lazy { retrofit.create(QuoteApiService::class.java) }

//        delay(4000L)
        mainDatabase.Dao().deleteAllNotes()

        val getFromNetwork = retrofitService.getQuotes()

        val response = getFromNetwork.awaitResponse()

        val newslist = response.body()
        if (newslist != null) {

            newslist.forEach { quotesData ->
                mainDatabase.Dao().insert(
                    Quote(
                        quotesData.id,
                        quotesData.userId,
                        quotesData.title,
                        quotesData.body
                    )
                )
            }
            println(
                "mainDatabase  " + mainDatabase.Dao()
                    .getAllNotes().size + "   " + response.body()!!.size
            )

            val loadFromLocal = mainDatabase.Dao().getAllNotesNew()
            emitAll(loadFromLocal)
        }
    }.flowOn(Dispatchers.IO)


}









































//fun loadItems(): LiveData<List<Quote>> {
//    CoroutineScope(Dispatchers.IO).launch {
//        mainDatabase.Dao().deleteAllNotes()
//    }
//    val retrofitService: QuoteApiService by lazy { retrofit.create(QuoteApiService::class.java) }
//
//    return liveData {
//
//        val getFromNetwork = retrofitService.getQuotes()
//
//        val response = getFromNetwork.awaitResponse()
//
//        val newslist = response.body()
//        if (newslist != null) {
//            withContext(Dispatchers.IO) {
//                newslist.forEach { quotesData ->
//                    mainDatabase.Dao().insert(
//                        Quote(
//                            quotesData.id,
//                            quotesData.userId,
//                            quotesData.title,
//                            quotesData.body
//                        )
//                    )
//                }
//
//                val loadFromLocal = mainDatabase.Dao().getAllNotesNew()
//                // emitSource(loadFromLocal)
//            }
//        }
//
//        /*   getFromNetwork.forEach{
//                quotesData ->
//            mainDatabase.Dao().insert(Quote(quotesData.id,quotesData.userId,quotesData.title,quotesData.body))
//        }
//
//        val loadFromLocal = mainDatabase.Dao().getAllNotesNew()
//
//        emitSource(loadFromLocal)*/
//
//    }
//}
