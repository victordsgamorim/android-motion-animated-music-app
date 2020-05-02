package com.victor.musicapp.presenter.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.victor.musicapp.R
import com.victor.musicapp.data.util.SharedPreferencesConstants.LONG_DEFAULT
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_ID
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_TIME
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_ACCESS_MAP
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_HEADER
import com.victor.musicapp.data.util.isExpired
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.presenter.ui.BaseActivity
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val controller by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityBinding()
        initViewModel()

        val event = generateNewOrSearchExistedToken()

        setViewModelObservers(event)
        navControllerDestinationChangedListener()
    }

    private fun generateNewOrSearchExistedToken(): MainStateEvent {
        val tokenTime = pref.getLong(GENERATED_TOKEN_TIME, LONG_DEFAULT)
        if (tokenTime == LONG_DEFAULT || tokenTime.isExpired()) {
            return generateNewTokenEvent()
        }
        return searchTokenDatabase()
    }

    private fun searchTokenDatabase(): MainStateEvent {
        val tokenId = pref.getLong(GENERATED_TOKEN_ID, LONG_DEFAULT)

        if (tokenId == LONG_DEFAULT) {
            throw IllegalArgumentException("Shared Preferences Token ID is classified as -1")
        }

        return SearchTokenDatabaseEvent(tokenId)
    }

    private fun setActivityBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun setViewModelObservers(event: MainStateEvent) {

        addEventToViewModel(event)

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChanged(dataState)

            dataState.data?.let { viewState ->

                viewState.spotifyArtistTrackRequest?.let { spotifyArtistTrackRequest ->
                    val spotifyArtistTrackRequestEvent =
                        SpotifyArtistTrackRequestEvent(spotifyArtistTrackRequest)
                    addEventToViewModel(spotifyArtistTrackRequestEvent)
                }

                viewState.spotifyApiResponse?.let { spotifyApiResponse ->
                    viewModel.setArtistTrackViewState(spotifyApiResponse)
                }
            }

        })

    }

    private fun addEventToViewModel(event: MainStateEvent) {
        viewModel.addStateEvent(event)
    }

    private fun generateNewTokenEvent(): MainStateEvent {
        val oAuthToken = OAuthToken(OAUTH_TOKEN_HEADER, OAUTH_TOKEN_ACCESS_MAP)
        return OAuthTokenEvent(oauthToken = oAuthToken)
    }

    private fun navControllerDestinationChangedListener() {
        controller.addOnDestinationChangedListener { _, _, _ ->
            viewModel.cancelJob()
        }
    }

    override fun onLoadingData(loading: Boolean) {
        if (loading) {
            activity_main_progress_bar.visibility = View.VISIBLE
        } else {
            activity_main_progress_bar.visibility = View.GONE
        }
    }
}
