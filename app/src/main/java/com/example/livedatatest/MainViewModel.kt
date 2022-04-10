package com.example.livedatatest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val questionCount = QuestionRepository.questionList.size
    val scoreLiveData = MutableLiveData(0)
    val questionNumberLiveData = MutableLiveData(0)
    val answer1 = MutableLiveData(4)
    val answer2= MutableLiveData((-10..100).random())
    val btnNextEnabledLiveData = MutableLiveData(true)
    val btnBackEnabledLiveData = MutableLiveData(true)
    val questionLiveData = MutableLiveData(QuestionRepository.questionList[0].text)
    val messageLiveData: LiveData<String> = Transformations.map(questionNumberLiveData) {
        when{
            questionNumberLiveData.value!! < questionCount/2 -> "Hurry up!"
            else -> "You're almost there!"
        }
    }

    fun nextClicked() {
        btnBackEnabledLiveData.value = true
        if (questionNumberLiveData.value!! < QuestionRepository.questionList.size) {
            answer1.value = QuestionRepository.questionList[questionNumberLiveData.value!!].correctAnswer
            answer2.value = (-10..100).random()
            questionNumberLiveData.value = questionNumberLiveData.value?.plus(1)
            if (questionNumberLiveData.value == questionCount)
                btnNextEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = QuestionRepository.questionList[it-1].text
            }

        }
    }
    fun backClicked(){
        btnNextEnabledLiveData.value = true
        if (questionNumberLiveData.value!! > 0){
            answer1.value = QuestionRepository.questionList[questionNumberLiveData.value!!].correctAnswer
            answer2.value = (-10..100).random()
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 0)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = QuestionRepository.questionList[it].text
            }
        }
    }
}