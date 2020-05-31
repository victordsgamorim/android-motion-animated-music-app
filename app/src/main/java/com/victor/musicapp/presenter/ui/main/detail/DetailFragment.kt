package com.victor.musicapp.presenter.ui.main.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.victor.musicapp.R
import com.victor.musicapp.databinding.FragmentDetailBinding
import com.victor.musicapp.presenter.util.getDominantColourFromImage
import com.victor.musicapp.presenter.ui.BaseFragment
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SearchForArtistDetails
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_details_bottom_boxes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var requestManager: RequestManager
    private val args by navArgs<DetailFragmentArgs>()

    private val trackItem by lazy {
        args.trackItem ?: throw IllegalArgumentException("Wrong Args")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_to_detail)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startTransitionBoxDetails()
        setToolbarBackStack()

        /**search for random artist*/
        val artistId = trackItem.artists.shuffled()[0].id
        viewModel.addStateEvent(SearchForArtistDetails(artistId))

        viewModel.viewState.observe(fragmentContext, Observer { viewState ->
            viewState.artist?.let { artist ->

                binding.albumTrackRate.text = artist.popularity.toString()

                if (artist.images.isNotEmpty()) {
                    requestManager.load(artist.images[0].url).into(binding.fragmentDetailAlbumCard)

                    CoroutineScope(IO).launch {
                        getDominantColourFromImage(fragmentContext, artist.images[0].url) { color ->
                            binding.exampleView.setBackgroundColor(color)
                        }
                    }

                }

            }

        })
    }

    private fun startTransitionBoxDetails() {
        fragment_detail_motion_container.setTransition(R.id.start, R.id.end)
        fragment_detail_motion_container.transitionToEnd()
    }

    private fun setToolbarBackStack() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        with(binding) {
            albumTrackName.isSelected = true
            albumTrackName.text = trackItem.name

            requestManager.load(trackItem.album.images[0].url)
                .into(mainAlbumCover)
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }


}