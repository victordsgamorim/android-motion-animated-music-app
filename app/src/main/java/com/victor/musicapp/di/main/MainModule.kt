package com.victor.musicapp.di.main

import com.victor.musicapp.repository.MainRepository
import com.victor.musicapp.ui.main.viewpager.adapter.ViewPagerAdapter
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