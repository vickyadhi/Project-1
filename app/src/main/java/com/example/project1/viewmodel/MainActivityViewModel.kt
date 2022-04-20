package com.example.project1.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project1.modelclass.ApiResponse
import com.example.project1.modelclass.Quote
import com.example.project1.retrofit.QuoteApiService
import com.example.project1.retrofit.retrofit
import com.example.project1.roomDB.DataBase
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class QuoteApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel() : ViewModel() {

    private val _status = MutableLiveData<QuoteApiStatus>()

    val status: LiveData<QuoteApiStatus> = _status

    private val _quote = MutableLiveData<List<Quote>>()
    lateinit var mainDatabase:DataBase

    val quoteList: LiveData<List<Quote>> = _quote
init {
    getQuote()
}

private fun getQuote(){

    val retrofitService: QuoteApiService by lazy { retrofit.create(QuoteApiService::class.java) }
    retrofitService.getQuotes()
        .enqueue(object : Callback<List<ApiResponse>> {
            override fun onResponse(
                @NonNull call: Call<List<ApiResponse>>,
                @NonNull response: Response<List<ApiResponse>>
            ) {

                println("onResponse  "+Gson().toJson(response.body()))
            }

            override fun onFailure(
                @NonNull call: Call<List<ApiResponse>>,
                @NonNull t: Throwable
            ) {
                println("onResponse  "+t.message)

            }
        })

}
}