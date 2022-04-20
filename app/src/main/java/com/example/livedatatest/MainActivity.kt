package com.example.livedatatest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.livedatatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        //observers:
        vModel.questionCount.observe(this) { binding.tvCount.text = it.toString() }
        vModel.progressBarMaxLiveData.observe(this){binding.progressBar.max = it ?: 2}
        vModel.questionNumberLiveData.observe(this) { binding.tvNumber.text = it.toString() }
        vModel.questionLiveData.observe(this) {
            if (it != null) {
                binding.tvQuestion.text = it.text
            }
        }
        vModel.btnNextEnabledLiveData.observe(this) { binding.btnNext.isEnabled = it }
        vModel.btnBackEnabledLiveData.observe(this) { binding.btnBack.isEnabled = it }
        //vModel.messageLiveData.observe(this){binding.tvMessage.text = it}
        vModel.scoreLiveData.observe(this) { binding.tvScore.text = it.toString() }

        //onClickListeners
        binding.btnRandom.setOnClickListener { vModel.newRandomQuestion() }
        binding.btnNext.setOnClickListener { vModel.nextClicked() }
        binding.btnBack.setOnClickListener { vModel.backClicked() }
        binding.btnAnswer.setOnClickListener { vModel.updateScore(binding.etAnswer.text.toString().toInt()) }




    }
}