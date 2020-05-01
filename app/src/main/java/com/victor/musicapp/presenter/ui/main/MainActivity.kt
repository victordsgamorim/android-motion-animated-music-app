package com.victor.musicapp.presenter.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.victor.musicapp.R
import com.victor.musicapp.data.util.SharedPreferencesConstants.DEFAULT_LONG
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_TIME
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_ACCESS_MAP
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_HEADER
import com.victor.musicapp.data.util.isExpired
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest
import com.victor.musicapp.presenter.ui.BaseActivity
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.OAuthTokenEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SpotifyArtistTrackRequestEvent
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pref: SharedPreferences

    private val controller by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityBinding()
        initViewModel()
        checkGeneratedTokenTimeOrNull()
        setViewModelObservers()
        navControllerDestinationChangedListener()

    }

    private fun checkGeneratedTokenTimeOrNull() {
        val tokenTime = pref.getLong(GENERATED_TOKEN_TIME, DEFAULT_LONG)

        if (tokenTime == DEFAULT_LONG || tokenTime.isExpired()) {
            generateNewToken()
        } else {
            // event that call from database
        }
    }

    private fun setActivityBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun setViewModelObservers() {
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

    private fun generateNewToken() {
        val oAuthToken = OAuthToken(OAUTH_TOKEN_HEADER, OAUTH_TOKEN_ACCESS_MAP)
        val event = OAuthTokenEvent(oauthToken = oAuthToken)
        viewModel.addStateEvent(event)
    }

    private fun navControllerDestinationChangedListener() {
        controller.addOnDestinationChangedListener { _, _, _ ->
            viewModel.cancelJob()
        }
    }
}
