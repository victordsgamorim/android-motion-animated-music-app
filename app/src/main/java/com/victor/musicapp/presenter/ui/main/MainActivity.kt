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
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.*
import kotlinx.android.synthetic.main.activity_main.*

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

        val event = CheckTokenIntegrityEvent
        addEventToViewModel(event)

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChanged(dataState)

            dataState.data?.let { viewState ->

                viewState.event?.let { state -> addEventToViewModel(state) }

                viewState.spotifyArtistTrackRequest?.let { spotifyArtistTrackRequest ->
                    val spotifyArtistTrackRequestEvent =
                        SpotifyArtistTrackRequestEvent(spotifyArtistTrackRequest)
                    addEventToViewModel(spotifyArtistTrackRequestEvent)
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
            activity_main_progress_bar.visibility = View.VISIBLE
        } else {
            activity_main_progress_bar.visibility = View.GONE
        }
    }
}
