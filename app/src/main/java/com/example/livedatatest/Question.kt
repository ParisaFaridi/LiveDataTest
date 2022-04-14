package com.example.livedatatest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val text:String,val correctAnswer:Int)