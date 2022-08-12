package com.example.mock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.mock.databinding.ActivitySplashBinding
import com.example.mock.vm.BudgetViewModel

class Splash : AppCompatActivity() {
    private lateinit var binding :ActivitySplashBinding
    private val mBudgetViewModel : BudgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mBudgetViewModel.getTotalAmountByType()



        supportActionBar?.hide()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}