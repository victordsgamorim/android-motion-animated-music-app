package com.victor.musicapp.presenter.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.victor.musicapp.databinding.FragmentAlbumBinding
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.presenter.ui.BaseFragment
import com.victor.musicapp.presenter.ui.main.album.viewpager.ViewPagerConfiguration
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.ViewPagerAdapter
import com.victor.musicapp.presenter.ui.main.album.viewpager.indicator.IndicatorConfiguration
import kotlinx.android.synthetic.main.start_item_motion_sample.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: ViewPagerAdapter
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModelObserver()
        itemClickListener()

    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val viewPagerConfiguration = ViewPagerConfiguration(fragmentContext)

        with(binding) {
            fragmentAlbumViewpager.adapter = adapter
            viewPagerConfiguration.initViewPagerConfig(fragmentAlbumViewpager)
        }
        return binding.root
    }

    private fun itemClickListener() {
        adapter.onItemClickListener = { trackItem ->
            fragmentTransition(
                token = token,
                trackItem = trackItem
            )
        }
    }

    private fun fragmentTransition(token: String, trackItem: TrackItem) {
        val extras = FragmentNavigatorExtras(
            main_album_cover to "album_cover",
            fragment_detail_album_card to "album_card"
        )

        val direction =
            AlbumFragmentDirections.actionAlbumFragmentToDetailFragment(
                token = token,
                trackItem = trackItem
            )

        findNavController().navigate(direction, extras)
    }

    private fun setViewModelObserver() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            val result = viewState.track?.items!!
            adapter.submitList(result)


            //init token variable
            viewState.track?.let { track ->
                token = "Bearer ${track.tokenId}"
            }

            val indicator =
                IndicatorConfiguration.create(
                    fragmentContext,
                    binding.fragmentAlbumIndicator,
                    adapter.itemCount
                )

            IndicatorConfiguration.setIndicatorOn(indicator.indicatorPosition)

            binding.fragmentAlbumViewpager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    IndicatorConfiguration.setIndicatorOn(position)
                }
            })
        })

    }
}