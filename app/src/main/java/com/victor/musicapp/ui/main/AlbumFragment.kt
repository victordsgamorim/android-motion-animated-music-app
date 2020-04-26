package com.victor.musicapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.victor.musicapp.R
import com.victor.musicapp.ui.BaseFragment
import com.victor.musicapp.ui.main.viewpager.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_album.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewpager.adapter = adapter

        setViewModelObserver()
        initViewPagerConfig()

    }

    private fun setViewModelObserver() {
        viewModel.getBandList().observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        })
    }

    private fun initViewPagerConfig() {
        viewpager.clipChildren = false
        viewpager.clipToPadding = false
        viewpager.offscreenPageLimit = 3
        viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    fun viewPagerAdjust() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))

        val pageMarginPx = resources.getDimensionPixelSize(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        transformer.addTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)

            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }

        viewpager.setPageTransformer(transformer)
    }
}