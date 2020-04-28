package com.victor.musicapp.presenter.di.main

import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.presenter.ui.main.viewpager.adapter.ViewPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainRepository(): MainRepository {
        return MainRepository()
    }

    @Provides
    fun provideAdapter(): ViewPagerAdapter {
        return ViewPagerAdapter()
    }
}