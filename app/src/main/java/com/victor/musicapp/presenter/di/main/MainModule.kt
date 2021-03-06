package com.victor.musicapp.presenter.di.main

import android.content.SharedPreferences
import com.victor.musicapp.data.api.SpotifyArtistService
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.database.dao.ArtistDao
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.database.dao.TrackDao
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.ViewPagerAdapter
import com.victor.musicapp.presenter.util.GlideImageLoader
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainRepository(
        service: SpotifyArtistTrackService,
        tokenService: SpotifyTokenService,
        prefEditor: SharedPreferences.Editor,
        pref: SharedPreferences,
        spotifyArtistTrackDao: SpotifyArtistTrackDao,
        trackDao: TrackDao,
        artistService: SpotifyArtistService,
        artistDao: ArtistDao
    ) =
        MainRepository(
            service,
            tokenService,
            prefEditor,
            pref,
            spotifyArtistTrackDao,
            trackDao,
            artistService,
            artistDao
        )

    @MainScope
    @Provides
    fun provideAdapter(imageLoader: GlideImageLoader) = ViewPagerAdapter(imageLoader)


}