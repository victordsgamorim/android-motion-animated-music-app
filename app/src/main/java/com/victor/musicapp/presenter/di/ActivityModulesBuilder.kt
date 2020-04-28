package com.victor.musicapp.presenter.di

import com.victor.musicapp.presenter.di.main.MainFragmentBuilder
import com.victor.musicapp.presenter.di.main.MainModule
import com.victor.musicapp.presenter.di.main.MainScope
import com.victor.musicapp.presenter.di.main.MainViewModelModule
import com.victor.musicapp.presenter.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModulesBuilder {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuilder::class, MainViewModelModule::class, MainModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}