package com.victor.musicapp.presenter.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.domain.dto.SpotifyArtistTrackRequest
import com.victor.musicapp.presenter.ui.BaseActivity
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SpotifyArtistTrackRequestEvent
import java.lang.NullPointerException

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.dataState.observe(this, Observer { viewState ->

            viewState.spotifyApiResponse?.let {
                viewModel.setArtistTrackViewState(it)
            } ?: throw NullPointerException("ViewState's DataState is null")

        })

    }

    override fun onStart() {
        super.onStart()

        viewModel.getNewToken().observe(this, Observer { spotifyTokenResponse ->

            val spotifyArtistTrackRequest = SpotifyArtistTrackRequest(
                authTokenType = spotifyTokenResponse.tokenType!!,
                authToken = spotifyTokenResponse.accessToken!!
            )
            val event = SpotifyArtistTrackRequestEvent(spotifyArtistTrackRequest)
            viewModel.addEvent(event)

        })
    }
}
