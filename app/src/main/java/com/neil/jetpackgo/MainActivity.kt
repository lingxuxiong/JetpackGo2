package com.neil.jetpackgo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.neil.jetpackgo.databinding.ActivityMainBinding
import vm.GreetingViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by lazy {
        ViewModelProvider(this).get(GreetingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.vm = viewModel

        // Set up life cycle owner of the binding, otherwise, the UI
        // won't get refreshed as a response to user click event
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        viewModel.changeGreeting("Changed greeting: Haha")
    }
}