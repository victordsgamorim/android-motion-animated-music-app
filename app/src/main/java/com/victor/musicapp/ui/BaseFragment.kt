package com.victor.musicapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.victor.musicapp.ui.main.MainViewModel
import com.victor.musicapp.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var factory: ViewModelProviderFactory
    protected lateinit var viewModel: MainViewModel

    protected val fragmentContext by lazy {
        activity?.run { this } ?: throw IllegalArgumentException("Invalid Activity!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(fragmentContext, factory).get(MainViewModel::class.java)
    }
}