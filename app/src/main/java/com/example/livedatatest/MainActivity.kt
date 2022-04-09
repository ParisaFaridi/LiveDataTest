package com.example.livedatatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

        binding.progressBar.max = vModel.questionCount

        val questionNumberObserver = Observer<Int> { number ->
            binding.tvNumber.text = number.toString()
            binding.progressBar.progress = number
        }
        val btnNextEnabledObserver = Observer<Boolean> { enabled ->
            binding.buttonNext.isEnabled = enabled
        }
        val btnBackEnabledObserver = Observer<Boolean> { enabled ->
            binding.buttonBack.isEnabled = enabled
        }
        val questionObserver = Observer<String> { question ->
            binding.tvQuestion.text = question
        }
        val messageLiveDataObserver = Observer<String> {
            binding.tvMessage.text = it
        }

        vModel.questionNumberLiveData.observe(this, questionNumberObserver)
        vModel.btnNextEnabledLiveData.observe(this,btnNextEnabledObserver)
        vModel.btnBackEnabledLiveData.observe(this,btnBackEnabledObserver)
        vModel.questionLiveData.observe(this,questionObserver)
        vModel.messageLiveData.observe(this,messageLiveDataObserver)

        binding.buttonNext.setOnClickListener { vModel.nextClicked() }
        binding.buttonBack.setOnClickListener {  vModel.backClicked()}
        binding.tvAnswer1.setOnClickListener { vModel.calScore() }
        binding.tvAnswer2.setOnClickListener { vModel.calScore() }

    }
}