package com.example.project1.modelclass

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Quote(
    @PrimaryKey(autoGenerate = true)
val id:Int,
val user_id:Int,
val title:String,
val body:String
)
