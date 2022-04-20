package com.example.livedatatest

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = QuestionRepository()
    var questionCount: LiveData<Int>

    init{
        repository.initDB(app.applicationContext)
        repository.addTestData()
        repository.getQuestionById(2)
        questionCount = repository.getCount() ?: MutableLiveData(0)
    }

    val questionLiveData = MutableLiveData(repository.newRandomQuestion())
    val questionNumberLiveData by lazy { MutableLiveData(questionLiveData.value?.id) }

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
        if (questionNumberLiveData.value!! > 0) {
            questionNumberLiveData.value = questionNumberLiveData.value?.minus(1)
            if (questionNumberLiveData.value == 0)
                btnBackEnabledLiveData.value = false
            questionNumberLiveData.value?.let {
                questionLiveData.value = repository.getQuestionById(it - 1)
                //repository.questionList[it].text
            }
        }
    }

    fun addScore(answer: Int) {
        if (isCorrect(answer)) {
            scoreLiveData.value = scoreLiveData.value?.plus(5)
        } else {
            scoreLiveData.value = scoreLiveData.value?.minus(2)
        }
    }

    private fun isCorrect(answer: Int): Boolean {
        return answer == questionLiveData.value?.correctAnswer
    }

    fun newRandomQuestion(){
        questionLiveData.value = repository.newRandomQuestion()
        repository.insert(questionLiveData.value!!)
    }
}

//class MainViewModel(app: Application):AndroidViewModel(app) {
//    var questionList :List<QuestionEntity>
//    val questionTextLiveData = MutableLiveData<String>()
//    val questionLiveData = MutableLiveData<QuestionEntity>()
//    var questionCountLiveData : LiveData<Int>
//    val numberLiveData= MutableLiveData<Int>(1)
//    //        MutableLiveData<String>(QuestionRepository.questionList[0].question)
//    init {
//        QuestionRepository.initDB(app.applicationContext)
//        questionList = QuestionRepository.getQuestions()
//        questionTextLiveData.value = QuestionRepository.getChosenQuestion(1).questionText
//        questionCountLiveData = QuestionRepository.getNumberOfQuestion()
//
//    }
//
//    fun nextQuestion(){
//        numberLiveData.value = numberLiveData.value?.plus(1)
//        numberLiveData.value?.let { number ->
//            questionTextLiveData.value = questionList[number].questionText
//        }
//    }
//    val questionCount = questionList.size -1
//    val scoreLiveData = MutableLiveData<Int>(0)
//    var halfQuestionListSize =questionList.size/2
//
//
//    val message :LiveData<String> = Transformations.map(numberLiveData) {
//        when (it) {
//            in 0..halfQuestionListSize -> "Hurry up"
//            else -> "You almost done"
//    }
//
//    var nextEnabledLiveData = MutableLiveData<Boolean>(true)
//
//    var backEnabledLiveData = MutableLiveData<Boolean>(false)
//
//    var checkAnswerEnableLiveData = MutableLiveData<Boolean>(true)
//
//    fun backClicked(){
//        checkAnswerEnableLiveData.value = true
//        nextEnabledLiveData.value =true
//        numberLiveData.value = numberLiveData.value?.minus(1)
//        numberLiveData.value?.let{number->
//            questionTextLiveData.value = QuestionRepository.getChosenQuestion(number).questionText
//        }
//
//        if (numberLiveData.value!! == 1) {
//            backEnabledLiveData.value = false
//        }
//
//    }
//    fun nextClicked(){
//        checkAnswerEnableLiveData.value = true
//        backEnabledLiveData.value = true
//        numberLiveData.value = numberLiveData.value?.plus(1)
//        numberLiveData.value?.let{number->
//            questionTextLiveData.value = QuestionRepository.getChosenQuestion(number).questionText
//        }
//        if (numberLiveData.value!! == questionCountLiveData.value) {
//            nextEnabledLiveData.value = false
//        }
//    }
//
//    fun checkAnswer(answer:Int) {
//        checkAnswerEnableLiveData.value = false
//        if(answer == QuestionRepository.getChosenQuestion(numberLiveData.value!!).answer){
//            scoreLiveData.value =   scoreLiveData.value?.plus(5)
//        }
//        else{
//            scoreLiveData.value =   scoreLiveData.value?.minus(2)
//        }
//    }
//    fun randomQuestion(){
//        QuestionRepository.newRandomQuestion()
//    }
//    fun getChosenQuestion(number:Int):LiveData<QuestionEntity>{
//        questionLiveData.value =  QuestionRepository.getChosenQuestion(number)
//        return questionLiveData
//    }
//    fun countQuestion():LiveData<Int>{
//        questionCountLiveData = QuestionRepository.getNumberOfQuestion()
//        return questionCountLiveData
//
//    }
//    fun addRandomQuestion():QuestionEntity{
//        QuestionRepository.addQuestion()
//        return QuestionRepository.newRandomQuestion()
//    }
//}