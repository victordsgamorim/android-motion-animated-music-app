package com.victor.musicapp.presenter.ui.main.album.viewpager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.victor.musicapp.R
import kotlin.math.abs

class ViewPagerConfiguration(private val context: Context) {

    private lateinit var pagetDimenstions: Pair<Float, Float>
    private val OFFSCREEN_PAGE_LIMIT = 3

    init {
        val pageMargin = context.resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = context.resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        pagetDimenstions = Pair(pageMargin, pageOffset)
    }


    fun initViewPagerConfig(viewPager: ViewPager2) {
        viewPager.clipChildren = false
        viewPager.clipToPadding = false
        viewPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        viewPager.setPageTransformer(viewPagerTransformer())
    }

    private fun viewPagerTransformer(): CompositePageTransformer {

        val (margin, offset) = pagetDimenstions

        val transfomer = CompositePageTransformer().apply {
            this.addTransformer { page, position ->
                itemPageScale(position, margin, offset, page)
            }
        }

        return transfomer
    }

    private fun itemPageScale(
        position: Float,
        pageMargin: Float,
        pageOffset: Float,
        page: View
    ) {
        val myOffset = position * -(2 * pageOffset + pageMargin)

        when {
            position < -1 -> {
                page.translationX = -myOffset
            }
            position <= 1 -> {
                val scale = 0.85f + ((1 - abs(position)) * 0.15f)

                page.translationX = myOffset
                page.scaleY = scale
                page.alpha = scale
            }
            else -> {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }
    }

}