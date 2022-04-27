package com.example.project1.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.example.project1.modelclass.ApiResponse
import com.example.project1.modelclass.Quote
import com.example.project1.retrofit.QuoteApiService
import com.example.project1.retrofit.retrofit
import com.example.project1.roomDB.Dao
import com.example.project1.roomDB.DataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

enum class QuoteApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel constructor(application: Application) : ViewModel() {
//    fun getQuotes() : Flow<List<Quote>> = Dao.getAllNotesNew()
//    fun insert() = Dao.getFromNetwork.forEach{
//            quotesData ->
//        mainDatabase.Dao().insert(Quote(quotesData.id,quotesData.userId,quotesData.title,quotesData.body))
//    }
//
//    val loadFromLocal = mainDatabase.Dao().getAllNotesNew()
//
//    emitSource(loadFromLocal)

     private val _status = MutableLiveData<QuoteApiStatus>()

    val status: LiveData<QuoteApiStatus> = _status

    lateinit var _quote: LiveData<List<Quote>>
    var mainDatabase: DataBase

    var userDataList: MutableLiveData<List<Quote>> = MutableLiveData()

    init {
        mainDatabase = DataBase.getDatabase(application)
        getQuote()
    }

    fun loadItems(): LiveData<List<Quote>> {
        CoroutineScope(Dispatchers.IO).launch {
            mainDatabase.Dao().deleteAllNotes()
        }
        val retrofitService: QuoteApiService by lazy { retrofit.create(QuoteApiService::class.java) }

        return liveData {

            val getFromNetwork = retrofitService.getQuotes()

            val response = getFromNetwork.awaitResponse()

            val newslist = response.body()
            if (newslist != null) {
                withContext(Dispatchers.IO) {
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

                    val loadFromLocal = mainDatabase.Dao().getAllNotesNew()
                    emitSource(loadFromLocal)
                }
            }

            /*   getFromNetwork.forEach{
                    quotesData ->
                mainDatabase.Dao().insert(Quote(quotesData.id,quotesData.userId,quotesData.title,quotesData.body))
            }

            val loadFromLocal = mainDatabase.Dao().getAllNotesNew()

            emitSource(loadFromLocal)*/

        }
    }

    private fun getQuote() {
        /* CoroutineScope(Dispatchers.IO).launch {
        mainDatabase.Dao().deleteAllNotes()
    }*/
        val retrofitService: QuoteApiService by lazy { retrofit.create(QuoteApiService::class.java) }
        retrofitService.getQuotes()
            .enqueue(object : Callback<List<ApiResponse>> {
                override fun onResponse(
                    @NonNull call: Call<List<ApiResponse>>,
                    @NonNull response: Response<List<ApiResponse>>
                ) {
                    if (response.code() == 200) {

                        CoroutineScope(Dispatchers.IO).launch {
                            mainDatabase.Dao().deleteAllNotes()
                            response.body()!!.forEach { quotesData ->
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
                            userDataList.postValue(mainDatabase.Dao().getAllNotes())

                        }
                    }
                }

                override fun onFailure(
                    @NonNull call: Call<List<ApiResponse>>,
                    @NonNull t: Throwable
                ) {

                }
            })

    }
}
