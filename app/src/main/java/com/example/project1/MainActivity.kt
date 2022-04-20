package com.example.project1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.project1.databinding.ActivityMainBinding
import com.example.project1.modelclass.Quote
import com.example.project1.roomDB.DataBase
import com.example.project1.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var mainViewModel:MainActivityViewModel
    lateinit var mainDatabase:DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mainDatabase = DataBase.getDatabase(application)
        mainDatabase.Dao().insert(Quote(0,1,"tt","yes"))

    }

}