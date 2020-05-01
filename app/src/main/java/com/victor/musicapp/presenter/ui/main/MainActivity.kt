package com.victor.musicapp.presenter.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.victor.musicapp.R
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_ACCESS_MAP
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_HEADER
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest
import com.victor.musicapp.presenter.ui.BaseActivity
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.*

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    private val controller by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityBinding()
        initViewModel()
        setViewModelObservers()
        navControllerDestinationChangedListener()

    }

    private fun setActivityBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun setViewModelObservers() {

        val oAuthToken = OAuthToken(OAUTH_TOKEN_HEADER, OAUTH_TOKEN_ACCESS_MAP)
        val event = OAuthTokenEvent(oauthToken = oAuthToken)
        viewModel.addStateEvent(event)

        viewModel.dataState.observe(this, Observer { dataState ->
            dataState.data?.let { viewState ->

                viewState.spotifyTokenResponse?.let { spotifyTokenResponse ->
                    val spotifyArtistTrackRequest = SpotifyArtistTrackRequest(
                        authTokenType = spotifyTokenResponse.tokenType!!,
                        authToken = spotifyTokenResponse.accessToken!!
                    )

                    val event = SpotifyArtistTrackRequestEvent(spotifyArtistTrackRequest)
                    viewModel.addStateEvent(event)
                }

                viewState.spotifyApiResponse?.let { spotifyApiResponse ->
                    viewModel.setArtistTrackViewState(spotifyApiResponse)
                }
            }

            dataState.error?.let {
                Log.e("DataState Error:", it)
            }
        })

    }

    private fun navControllerDestinationChangedListener() {
                controller.addOnDestinationChangedListener { _, _, _ ->
                    viewModel.cancelJob()
        }
    }

}
