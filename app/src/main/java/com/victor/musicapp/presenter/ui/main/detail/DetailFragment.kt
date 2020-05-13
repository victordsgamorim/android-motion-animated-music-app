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
import com.bumptech.glide.RequestManager
import com.victor.musicapp.R
import com.victor.musicapp.databinding.FragmentDetailBinding
import com.victor.musicapp.presenter.ui.BaseFragment
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.*
import kotlinx.android.synthetic.main.fragment_detail.*
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
        setToolbarBackStack()

        /**search for random artist*/
        val artistId = trackItem.artists.shuffled()[0].id
        viewModel.addStateEvent(SearchForArtistDetails(artistId))



        viewModel.viewState.observe(fragmentContext, Observer { viewState ->
            viewState.artist?.let { artist ->
                Log.e("DetailFragment", "RecebendoArtist: $artist")
            }

        })
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