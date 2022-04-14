package com.example.livedatatest

import androidx.lifecycle.LiveData

class QuestionRepository(private val questionDao: QuestionDao) {

    val questionList = questionDao.getAll()

    //    fun addQuestion() {
//        questionDao.addQuestion(
//            Question(0, "2+2", 4), Question(0, "5-2", 3),
//            Question(0, "8+10", 18), Question(0, "3*2", 6), Question(0, "7-6", 1),
//            Question(0, "1+8", 9), Question(0, "5-6", -1), Question(0, "10*10", 100),
//            Question(0, "9-6", 3), Question(0, "4*8", 32)
//        )
//    }
    fun addTestData() {
        for (i in 0..9)
            newRandomQuestion()
    }

    fun newRandomQuestion() {
        val a = (1..100).random()
        val b = (1..50).random()
        questionDao.addQuestion(Question(0, "$a - $b", a - b))
    }

    fun getCount(): LiveData<Int> {
        return questionDao.getCount()
    }

    fun getQuesitonById(id: Int): Question {
        return questionDao.getQuestionById(id)
    }
}