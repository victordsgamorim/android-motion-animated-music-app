package com.victor.musicapp.presenter.di.main

import com.victor.musicapp.presenter.ui.main.album.AlbumFragment
import com.victor.musicapp.presenter.ui.main.detail.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeAlbumFragment(): AlbumFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

}