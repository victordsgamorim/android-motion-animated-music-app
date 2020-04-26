package com.victor.musicapp.di.main

import com.victor.musicapp.ui.main.AlbumFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeAlbumFragment(): AlbumFragment
}