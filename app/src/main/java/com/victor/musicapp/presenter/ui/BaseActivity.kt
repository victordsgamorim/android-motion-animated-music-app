package com.victor.musicapp.presenter.ui

import android.util.Log
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.data.util.OnDataStateChangeListener
import com.victor.musicapp.presenter.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), OnDataStateChangeListener {

    @Inject
    protected lateinit var factory: ViewModelProviderFactory
    override fun onDataStateChanged(dataState: DataState<*>) {
        onLoadingData(dataState.loading)

        dataState.error?.let {
            Log.e("DataState Error:", it)
        }
    }

    abstract fun onLoadingData(loading: Boolean)


}