package com.example.project1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.databinding.ActivityMainBinding
import com.example.project1.factory.MainFactory
import com.example.project1.modelclass.Quote
import com.example.project1.retrofit.RecyclerViewAdapter
import com.example.project1.roomDB.DataBase
import com.example.project1.viewmodel.MainActivityViewModel



class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding : ActivityMainBinding
    lateinit var mainViewModel:MainActivityViewModel
    lateinit var mainDatabase:DataBase
    lateinit var quoteList:ArrayList<Quote>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        quoteList= ArrayList()
        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        recyclerViewAdapter= RecyclerViewAdapter(applicationContext,quoteList)
        binding.recyclerView.adapter=recyclerViewAdapter

        mainViewModel= ViewModelProvider(this, MainFactory(application)).get(MainActivityViewModel::class.java)
        mainViewModel.loadItems().observe(this, androidx.lifecycle.Observer { userDataList ->
            quoteList.clear()
            quoteList.addAll(userDataList)
            recyclerViewAdapter.notifyDataSetChanged()

            println("apiResponse  "+userDataList.size)
        })

    }

}