package com.victor.musicapp.presenter.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.victor.musicapp.R
import com.victor.musicapp.databinding.ActivityMainBinding
import com.victor.musicapp.presenter.ui.BaseActivity
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.CheckTokenIntegrityEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SpotifyArtistTrackRequestEvent

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
        checkTokenIntegrity()
        setViewModelObservers()
        navControllerDestinationChangedListener()
    }

    private fun checkTokenIntegrity() {
        addEventToViewModel(CheckTokenIntegrityEvent)
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
            onDataStateChanged(dataState)

            dataState.data?.let { viewState ->

                viewState.event?.let { state -> addEventToViewModel(state) }

                //Search for Artist Album
                viewState.spotifyArtistTrackRequest?.let { spotifyArtistTrackRequest ->
                    val spotifyArtistTrackRequestEvent =
                        SpotifyArtistTrackRequestEvent(spotifyArtistTrackRequest)
                    addEventToViewModel(spotifyArtistTrackRequestEvent)
                }


                if (viewState.track != null && viewState.token != null) {
                    val event =
                        MainStateEvent.InsertArtistsToDatabase(viewState.token!!, viewState.track!!)
                    addEventToViewModel(event)
                }

                viewState.track?.let { track ->
                    viewModel.setTrackViewState(track)
                }
            }

        })

    }

    private fun addEventToViewModel(event: MainStateEvent) {
        viewModel.addStateEvent(event)
    }

    private fun navControllerDestinationChangedListener() {
        controller.addOnDestinationChangedListener { _, _, _ ->
            viewModel.cancelJob()
        }
    }

    override fun onLoadingData(loading: Boolean) {
        if (loading) {
            binding.activityMainProgressBar.visibility = View.VISIBLE
        } else {
            binding.activityMainProgressBar.visibility = View.GONE
        }
    }
}
