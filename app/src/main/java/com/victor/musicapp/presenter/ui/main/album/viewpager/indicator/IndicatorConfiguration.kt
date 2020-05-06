package com.victor.musicapp.presenter.ui.main.album.viewpager.indicator

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.victor.musicapp.R

class IndicatorConfiguration private constructor(
    private val size: Int
) {

    private var _indicatorPosition = containerPosition

    val indicatorPosition: Int
        get() = _indicatorPosition

    init {
        setIndicators()
    }

    private fun setIndicators() {
        val indicators = arrayOfNulls<ImageView>(size)
        val layout = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layout.setMargins(8, 0, 0, 0)

        for (i in 0 until size) {
            indicators[i] = ImageView(context)

            indicators[i]?.setDrawableIndicator(
                context,
                R.drawable.bottom_indicator_inactive
            )
            indicators[i]?.layoutParams = layout
            container.addView(indicators[i])
        }
    }

    companion object {

        private var containerPosition = 0
        private lateinit var context: Context
        private lateinit var container: LinearLayout

        fun create(context: Context, container: LinearLayout, size: Int): IndicatorConfiguration {
            this.context = context
            this.container = container
            return IndicatorConfiguration(size)
        }


        @JvmStatic
        fun setIndicatorOn(index: Int) {
            val childCount = container.childCount
            for (i in 0 until childCount) {
                val imageView = container.getChildAt(i) as ImageView

                when (i) {
                    index -> {
                        containerPosition = index
                        imageView.setDrawableIndicator(
                            context,
                            R.drawable.bottom_indicator_active
                        )
                    }
                    else -> imageView.setDrawableIndicator(
                        context,
                        R.drawable.bottom_indicator_inactive
                    )
                }
            }
        }

    }


}

private fun ImageView.setDrawableIndicator(context: Context, drawable: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawable))
}
