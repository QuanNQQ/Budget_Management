package com.example.mock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.example.mock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var controller : NavController
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
//        controller = navHost.navController
//
//        binding.navView.setupWithNavController(controller)



    }
}