package com.victor.musicapp.presenter.di.main

import com.bumptech.glide.RequestManager
import com.victor.musicapp.data.api.SpotifyService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.presenter.ui.main.viewpager.adapter.ViewPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainRepository(service: SpotifyService, tokenService: SpotifyTokenService) =
        MainRepository(service, tokenService)

    @MainScope
    @Provides
    fun provideAdapter(requestManager: RequestManager) = ViewPagerAdapter(requestManager)

}