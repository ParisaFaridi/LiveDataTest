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

        binding.progressBar.max = 5

        vModel.questionNumberLiveData.observe(this) {
            binding.tvNumber.text = it.toString()
            if (it != null) {
                binding.progressBar.progress = it
            }
        }
        vModel.btnNextEnabledLiveData.observe(this) { binding.btnNext.isEnabled = it }
        vModel.btnBackEnabledLiveData.observe(this) { binding.btnBack.isEnabled = it }
        vModel.questionLiveData.observe(this) { binding.tvQuestion.text = it.text }
        vModel.scoreLiveData.observe(this) { binding.tvQuestion.text = it.toString() }
        vModel.questionCount.observe(this) { binding.tvCount.text = it.toString() }
        //vModel.messageLiveData.observe(this){binding.tvMessage.text = it}

        binding.btnNext.setOnClickListener {
            if (binding.etAnswer.text.toString().isEmpty())
                return@setOnClickListener
            vModel.nextLevel(binding.etAnswer.text.toString().toInt())
            vModel.nextClicked()
        }
        binding.btnBack.setOnClickListener { vModel.backClicked() }

        binding.btnRandom.setOnClickListener { vModel.newRandomQuestion() }


    }
}