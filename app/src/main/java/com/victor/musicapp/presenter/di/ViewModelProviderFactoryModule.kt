package com.victor.musicapp.presenter.di

import androidx.lifecycle.ViewModelProvider
import com.victor.musicapp.presenter.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelProviderFactoryModule {

    @Binds
    abstract fun bindViewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}