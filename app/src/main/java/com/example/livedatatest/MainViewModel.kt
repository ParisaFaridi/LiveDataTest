package com.example.livedatatest

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = QuestionRepository()
    var questionCount: LiveData<Int>

    init {
        repository.initDB(app.applicationContext)
        repository.addTestData()
        questionCount = repository.getCount() ?: MutableLiveData(0)
    }

    val questionLiveData by lazy { MutableLiveData(repository.getQuestionById(0)) }
    val questionNumberLiveData by lazy { MutableLiveData(0) }
    val progressBarMaxLiveData by lazy { MutableLiveData(repository.getCount()?.value) }
    val btnNextEnabledLiveData = MutableLiveData(true)
    val btnBackEnabledLiveData = MutableLiveData(false)
    val messageLiveData by lazy {
        Transformations.map(questionNumberLiveData) {
            when {
                questionLiveData.value == null ->"Hurry up!"
                questionNumberLiveData.value!! < questionCount.value?.div(2)!! -> "Hurry up!"
                else -> "You're almost there!"
            }
        }
    }

    fun newRandomQuestion() {
        questionLiveData.value = repository.newRandomQuestion()
        questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)
        progressBarMaxLiveData.value = progressBarMaxLiveData.value?.plus(1)
        repository.insert(questionLiveData.value!!)
    }

    fun nextClicked() {
        btnBackEnabledLiveData.value = true
        if (questionNumberLiveData.value!! < questionCount.value!!) {
            questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)
            if (questionNumberLiveData.value == questionCount.value)
                btnNextEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it - 1)
            }
        }
    }

    fun backClicked() {
        btnNextEnabledLiveData.value = true
        if (questionNumberLiveData.value!! > 0) {
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 0)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it - 1)
            }
        }
    }

//    val scoreLiveData = MutableLiveData(0)

//    fun addScore(answer: Int) {
//        if (isCorrect(answer)) {
//            scoreLiveData.value = scoreLiveData.value?.plus(5)
//        } else {
//            scoreLiveData.value = scoreLiveData.value?.minus(2)
//        }
//    }
//
//    private fun isCorrect(answer: Int): Boolean {
//        return answer == questionLiveData.value?.correctAnswer
//    }
}