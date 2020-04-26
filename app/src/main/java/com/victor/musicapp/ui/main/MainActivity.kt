package com.victor.musicapp.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.victor.musicapp.R
import com.victor.musicapp.ui.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }


}
