package com.example.project1.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.project1.modelclass.Quote

@Dao
interface Dao {

    @Insert
    fun insert(note: Quote)

    @Update
    fun update(note: Quote)

    @Delete
    fun delete(note: Quote)

    @Query("DELETE FROM users")
    fun deleteAllNotes()

    @Query("select * from users")
    fun getAllNotes(): List<Quote>

    @Query("select * from users")
    fun getAllNotesNew(): LiveData<List<Quote>>

}