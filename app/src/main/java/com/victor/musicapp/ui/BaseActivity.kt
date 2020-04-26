package com.victor.musicapp.ui

import com.victor.musicapp.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var factory: ViewModelProviderFactory
}