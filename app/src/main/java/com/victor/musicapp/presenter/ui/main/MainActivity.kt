package com.victor.musicapp.presenter.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.presenter.ui.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }


}
