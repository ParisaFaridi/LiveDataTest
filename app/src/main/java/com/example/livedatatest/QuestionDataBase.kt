package com.example.livedatatest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class], version = 1)
abstract class QuestionDataBase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {

        private var INSTANCE: QuestionDataBase? = null

        fun getDataBase(context: Context): QuestionDataBase? {
            if (INSTANCE == null) {
                synchronized(QuestionDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDataBase::class.java, "user.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
