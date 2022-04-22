package com.example.livedatatest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.livedatatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val vModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()

    }

    private fun initViews() {

        //observers:
        vModel.questionCount.observe(this) {
            binding.questionCount = it
            binding.progressBar.max = it

        }
        vModel.questionNumberLiveData.observe(this) {
            binding.questionNumber = it
            binding.progressBar.progress = it
        }
        vModel.questionLiveData.observe(this) {
            if (it != null) {
                binding.question = it.text
            }
        }
        vModel.btnNextEnabledLiveData.observe(this) { binding.btnNext.isEnabled = it }
        vModel.btnBackEnabledLiveData.observe(this) { binding.btnBack.isEnabled = it }
        vModel.scoreLiveData.observe(this) { binding.score = it}

        //onClickListeners
        binding.btnRandom.setOnClickListener { vModel.newRandomQuestion() }
        binding.btnNext.setOnClickListener { vModel.nextClicked() }
        binding.btnBack.setOnClickListener { vModel.backClicked() }
        binding.btnAnswer.setOnClickListener {
            vModel.updateScore(
                binding.etAnswer.text.toString().toInt()
            )
        }

        //vModel.messageLiveData.observe(this){binding.tvMessage.text = it}

        vModel.questionList.observe(this){
            if ( it != null){
                val adapter = QuestionAdapter()
                binding.questionRv.adapter = adapter
                adapter.submitList(it)
            }
        }
    }
}