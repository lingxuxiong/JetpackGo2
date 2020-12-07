package com.neil.jetpackgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neil.jetpackgo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.welcome.text = "Hello View Binding"

        setContentView(binding.root)
    }
}