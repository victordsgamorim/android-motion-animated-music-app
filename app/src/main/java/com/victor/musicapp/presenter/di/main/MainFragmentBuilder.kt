package com.victor.musicapp.presenter.di.main

import com.victor.musicapp.presenter.ui.main.AlbumFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeAlbumFragment(): AlbumFragment
}