package com.victor.musicapp.presenter.ui

import com.victor.musicapp.presenter.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var factory: ViewModelProviderFactory
}