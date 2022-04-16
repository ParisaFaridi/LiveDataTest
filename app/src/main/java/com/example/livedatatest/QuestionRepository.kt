package com.example.livedatatest

import androidx.lifecycle.LiveData

class QuestionRepository(private val questionDao: QuestionDao) {

    val questionList = questionDao.getAll()

    fun addTestData() {
        for (i in 0..9)
            questionDao.insert(newRandomQuestion())
    }
    fun addQuestion(question: Question){
        questionDao.insert(question)
    }

    fun newRandomQuestion(): Question {
        val a = (1..100).random()
        val b = (1..50).random()
        return Question(0, "$a - $b", a - b)
    }

    fun getCount(): LiveData<Int> {
        return questionDao.getCount()
    }

    fun getQuestionById(id: Int): Question {
        return questionDao.getQuestionById(id)
    }
}