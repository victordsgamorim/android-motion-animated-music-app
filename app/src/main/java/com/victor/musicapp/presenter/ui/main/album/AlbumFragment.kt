package com.victor.musicapp.presenter.ui.main.album

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.victor.musicapp.R
import com.victor.musicapp.databinding.FragmentAlbumBinding
import com.victor.musicapp.presenter.ui.BaseFragment
import com.victor.musicapp.presenter.ui.main.album.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_album.*
import kotlinx.android.synthetic.main.start_item_motion_sample.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: ViewPagerAdapter
//    private lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentAlbumBinding.inflate(inflater, container, false)
//        binding.fragmentAlbumViewpager.adapter = adapter
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        setViewModelObserver()
//        initViewPagerConfig()

        fragment_album_viewpager.adapter = adapter

        setViewModelObserver()

        adapter.onItemClickListener = {
            val extras = FragmentNavigatorExtras(
                main_album_cover to "album_cover",
                main_album_card to "album_card"
            )

            findNavController().navigate(
                R.id.action_albumFragment_to_detailFragment,
                null,
                null,
                extras
            )
        }

    }

    private fun setViewModelObserver() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            val result = viewState.track?.items!!
            adapter.submitList(result)


        })

    }

//    private fun setViewModelObserver() {
//
//        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
//
//            val result = viewState.track?.items!!
//            adapter.submitList(result)
//
//            setIndicators(adapter.itemCount)
//            setIndicatorOn(0)
//
//            binding.fragmentAlbumViewpager.registerOnPageChangeCallback(object :
//                ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    setIndicatorOn(position)
//                }
//            })
//        })
//
//    }
//
//    //This logic must be refactored in another class
//    private fun setIndicators(size: Int) {
//        val indicators = arrayOfNulls<ImageView>(size)
//        val layout = LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        layout.setMargins(8, 0, 0, 0)
//
//        for (i in 0 until size) {
//            indicators[i] = ImageView(fragmentContext)
//
//            indicators[i]?.setDrawableIndicator(
//                fragmentContext,
//                R.drawable.bottom_indicator_inactive
//            )
//            indicators[i]?.layoutParams = layout
//            binding.fragmentAlbumIndicator.addView(indicators[i])
//        }
//    }
//
//    private fun setIndicatorOn(index: Int) {
//        val childCount = binding.fragmentAlbumIndicator.childCount
//        for (i in 0 until childCount) {
//            val imageView = binding.fragmentAlbumIndicator.getChildAt(i) as ImageView
//
//            when (i) {
//                index -> imageView.setDrawableIndicator(
//                    fragmentContext,
//                    R.drawable.bottom_indicator_active
//                )
//                else -> imageView.setDrawableIndicator(
//                    fragmentContext,
//                    R.drawable.bottom_indicator_inactive
//                )
//            }
//        }
//    }
//
//    private fun initViewPagerConfig() {
//        fragment_album_viewpager.clipChildren = false
//        fragment_album_viewpager.clipToPadding = false
//        fragment_album_viewpager.offscreenPageLimit = 3
//        fragment_album_viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//    }
//
//    private fun ImageView.setDrawableIndicator(context: Context, drawable: Int) {
//        this.setImageDrawable(ContextCompat.getDrawable(context, drawable))
//    }

}