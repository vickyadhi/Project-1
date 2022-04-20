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

    @Query("delete from users")
    fun deleteAllNotes()

    @Query("select * from users ")
    fun getAllNotes(): LiveData<List<Quote>>
/*

@Insert(onConflict = OnConflictStrategy.IGNORE)
    fun userList (quote: Quote)

    @Query("SELECT * FROM user's ORDER BY id ASC")
    fun allData() : LiveData<List<Quote>>
*/

}