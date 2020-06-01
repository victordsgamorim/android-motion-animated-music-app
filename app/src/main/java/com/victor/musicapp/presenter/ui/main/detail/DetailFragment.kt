package com.victor.musicapp.presenter.ui.main.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.victor.musicapp.R
import com.victor.musicapp.databinding.FragmentDetailBinding
import com.victor.musicapp.presenter.ui.BaseFragment
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SearchForArtistDetails
import com.victor.musicapp.presenter.util.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var imageLoader: GlideImageLoader

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

        /**search for random artist event*/
        val artistId = trackItem.artists.shuffled()[0].id
        viewModel.addStateEvent(SearchForArtistDetails(artistId))


        /**artist data response through event*/
        viewModel.viewState.observe(fragmentContext, Observer { viewState ->
            viewState.artist?.let { artist ->

                /**artist rate*/
                binding.fragmentDetailExtendedImage.albumTrackRate.text =
                    artist.popularity.convertPopularityToDouble()

                /**artist toolbar*/
                if (artist.images.isNotEmpty()) {

                    /**extended artist photo at toolbar*/
                    imageLoader.setUrlToImage(
                        artist.images[0].url,
                        binding.fragmentDetailExtendedImage.fragmentDetailAlbumCard
                    )

                    /**dominant colour after image added to toolbar*/
                    setDominantColourToView(fragmentContext, artist.images[0].url) { colour ->
                        setColourToView(colour)
                    }

                } else {
                    /**if there is no artist cover image, load from album cover*/
                    imageLoader.setUrlToImage(
                        trackItem.album.images[0].url,
                        binding.fragmentDetailExtendedImage.fragmentDetailAlbumCard
                    )

                    setDominantColourToView(
                        fragmentContext,
                        trackItem.album.images[0].url
                    ) { colour ->
                        setColourToView(colour)
                    }
                }

            }

        })
    }


    private fun setColourToView(colour: Int) {
        with(binding) {
            /**set colour to collapsed toolbar*/
            fragmentDetailCollapsingtoolbar.setContentScrimColor(colour)
            fragmentDetailCollapsingtoolbar.setStatusBarScrimColor(colour)

            /**set colour to material design boxes*/
            fragmentDetailsBottomBoxes.bandDetailsAbout.setCardBackgroundColor(colour)
            fragmentDetailsBottomBoxes.bandDetailsIcons.setCardBackgroundColor(colour)
        }
    }

    private fun startTransitionBoxDetails() {
        with(binding.fragmentDetailsBottomBoxes.fragmentDetailMotionContainer) {
            setTransition(R.id.start, R.id.end)
            transitionToEnd()
        }

        with(binding.fragmentDetailExtendedImage.fragmentDetailMotionContainerToolbar) {
            setTransition(R.id.start, R.id.end)
            transitionToEnd()
        }

    }

    private fun setToolbarBackStack() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(fragment_detail_toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        with(binding) {
            fragmentDetailExtendedImage.albumTrackName.isSelected = true
            fragmentDetailExtendedImage.albumTrackName.text = trackItem.name
            imageLoader.setUrlToImage(
                trackItem.album.images[0].url,
                fragmentDetailExtendedImage.mainAlbumCover
            )
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