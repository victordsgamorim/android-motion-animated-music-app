package com.victor.musicapp.di

import com.victor.musicapp.di.main.MainFragmentBuilder
import com.victor.musicapp.di.main.MainModule
import com.victor.musicapp.di.main.MainScope
import com.victor.musicapp.di.main.MainViewModelModule
import com.victor.musicapp.ui.main.MainActivity
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