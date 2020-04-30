package com.victor.musicapp.presenter.di.main

import com.bumptech.glide.RequestManager
import com.victor.musicapp.data.api.SpotifyService
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.presenter.ui.main.viewpager.adapter.ViewPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainRepository(service: SpotifyService) = MainRepository(service)

    @Provides
    fun provideAdapter(requestManager: RequestManager) = ViewPagerAdapter(requestManager)

}