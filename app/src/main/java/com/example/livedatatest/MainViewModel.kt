package com.example.livedatatest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val questionCount = QuestionRepository.questionList.size

    val questionNumberLiveData = MutableLiveData(0)
    val btnNextEnabledLiveData = MutableLiveData(true)
    val btnBackEnabledLiveData = MutableLiveData(true)
    val questionLiveData = MutableLiveData(QuestionRepository.questionList[0].text)

    fun nextClicked() {
        btnBackEnabledLiveData.value = true
        if (questionNumberLiveData.value!! < QuestionRepository.questionList.size) {
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
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 0)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = QuestionRepository.questionList[it].text
            }
        }
    }
}