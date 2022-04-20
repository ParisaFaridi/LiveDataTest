package com.example.livedatatest.model

import android.content.Context
import androidx.lifecycle.LiveData

class QuestionRepository {

    private var questionDataBase: QuestionDataBase? = null
    private var questionDao: QuestionDao? = null
    lateinit var questionList: LiveData<List<Question>>

    fun initDB(context: Context) {

        questionDataBase = QuestionDataBase.getDataBase(context)
        questionDao = questionDataBase?.questionDao()
        questionList = questionDao?.getAll()!!
    }

    fun addTestData() {
        for (i in 0..9)
            questionDao?.insert(newRandomQuestion())
    }

    fun insert(vararg question: Question) {
        questionDao?.insert(*question)
    }

    fun newRandomQuestion(): Question {
        val a = (1..100).random()
        val b = (1..50).random()
        return Question(0, "$a - $b", a - b)
    }

    fun getCount(): LiveData<Int>? {
        return questionDao?.getCount()
    }

    fun getQuestionById(id: Int): Question? {
        return questionDao?.getQuestionById(id)
    }
}