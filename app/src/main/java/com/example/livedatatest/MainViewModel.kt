package com.example.livedatatest

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(app: Application):AndroidViewModel(app) {

    private val repository: QuestionRepository
    var questionCount : LiveData<Int>
    init {
        val questionDao = QuestionDataBase.getDataBase(app)?.questionDao()
        repository = questionDao?.let { QuestionRepository(it) }!!
        repository.addTestData()
        repository.getQuestionById(2)
        questionCount = repository.getCount()
    }

    val questionLiveData = MutableLiveData(repository.newRandomQuestion())
    val questionNumberLiveData by lazy{ MutableLiveData(questionLiveData.value?.id)}

    val scoreLiveData = MutableLiveData(0)

    val btnNextEnabledLiveData = MutableLiveData(true)
    val btnBackEnabledLiveData = MutableLiveData(true)
//    val messageLiveData: LiveData<String> = Transformations.map(questionNumberLiveData) {
//        when{
//            questionNumberLiveData.value!! < questionCount.value?.div(2)!! -> "Hurry up!"
//            else -> "You're almost there!"
//        }
//    }

    fun nextClicked() {
        btnBackEnabledLiveData.value = true
        if (questionNumberLiveData.value!! < questionCount.value!!) {
            questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)

            if (questionNumberLiveData.value == questionCount.value)
                btnNextEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it -1)
                    //repository.questionList[it-1].text
            }
        }
    }
    fun backClicked(){
        btnNextEnabledLiveData.value = true
        if (questionNumberLiveData.value!! > 0){
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 0)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it -1)
                    //repository.questionList[it].text
            }
        }
    }

    fun nextLevel(answer: Int){
        if (isCorrect(answer)) {
            scoreLiveData.value = scoreLiveData.value?.plus(5)
        } else {
            scoreLiveData.value = scoreLiveData.value?.minus(2)
        }
    }

    private fun isCorrect(answer:Int): Boolean {
        return answer == questionLiveData.value?.correctAnswer
    }

    fun newRandomQuestion(){
        questionLiveData.value = repository.newRandomQuestion()
        repository.addQuestion(questionLiveData.value!!)
    }
}