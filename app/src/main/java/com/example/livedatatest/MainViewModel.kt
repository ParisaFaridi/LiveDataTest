package com.example.livedatatest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = QuestionRepository()
    var questionCount: LiveData<Int>

    init {
        repository.initDB(app.applicationContext)
        repository.addTestData()
        questionCount = repository.getCount() ?: MutableLiveData(0)
    }

    val questionLiveData by lazy { MutableLiveData(repository.getQuestionById(1)) }
    val questionNumberLiveData by lazy { MutableLiveData(1) }
    val btnNextEnabledLiveData = MutableLiveData(true)
    val btnBackEnabledLiveData = MutableLiveData(false)
    val scoreLiveData = MutableLiveData(0)

    fun newRandomQuestion() {
        questionLiveData.value = repository.newRandomQuestion()
        questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)
        repository.insert(questionLiveData.value!!)
    }

    fun nextClicked() {
        btnBackEnabledLiveData.value = true
        if (questionNumberLiveData.value!! < questionCount.value!!) {
            questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)
            if (questionNumberLiveData.value == questionCount.value)
                btnNextEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it)
            }
        }
    }

    fun backClicked() {
        btnNextEnabledLiveData.value = true
        if (questionNumberLiveData.value!! > 1) {
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 1)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it)
            }
        }
    }
    fun updateScore(answer: Int) {
        if (isCorrect(answer)) {
            scoreLiveData.value = scoreLiveData.value?.plus(5)
        } else {
            scoreLiveData.value = scoreLiveData.value?.minus(2)
        }
    }

    private fun isCorrect(answer: Int): Boolean {
        return answer == questionLiveData.value?.correctAnswer
    }

//    val messageLiveData by lazy {
//        Transformations.map(questionNumberLiveData) {
//            when {
//                questionLiveData.value == null -> "Hurry up!"
//                questionNumberLiveData.value!! < questionCount.value?.div(2)!! -> "Hurry up!"
//                else -> "You're almost there!"
//            }
//        }
//    }
}