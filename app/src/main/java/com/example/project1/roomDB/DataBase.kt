package com.example.project1.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project1.modelclass.Quote

@Database(entities = [Quote::class],version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase(){
       abstract fun Dao() : Dao
       companion object {
           @Volatile
           private var INSTANCE : DataBase?= null
           fun getDatabase(context: Context) : DataBase {
               val tempInstance = INSTANCE
               if (tempInstance != null) {
                   return tempInstance
               }
               synchronized(this) {
                   val instance = Room.databaseBuilder(
                       context.applicationContext,
                       DataBase::class.java,
                       "database"
                   ).build()
                   INSTANCE = instance
                   return instance
               }
           }
       }
}