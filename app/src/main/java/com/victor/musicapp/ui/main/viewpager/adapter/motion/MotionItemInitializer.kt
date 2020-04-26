package com.victor.musicapp.ui.main.viewpager.adapter.motion

import android.content.Context
import android.view.View
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.victor.musicapp.R
import com.victor.musicapp.util.animationLoop
import com.victor.musicapp.util.setAnimationToDrawable
import kotlinx.android.synthetic.main.end_motion_extended_card_info.view.*
import kotlinx.android.synthetic.main.start_item_motion_band.view.*

data class MotionItemInitializer(private val context: Context, private val view: View) {

    private var isClicked = true

    private val cardWaveForward by lazy {
        AnimatedVectorDrawableCompat.create(
            context,
            R.drawable.animated_card_wave_forward
        )
    }

    private val cardWaveBackward by lazy {
        AnimatedVectorDrawableCompat.create(
            context,
            R.drawable.animated_card_wave_backward
        )
    }

    private fun setWaveAnimationToCard() {
        view.album_card_wave_animation.setAnimationToDrawable(cardWaveForward)
        view.album_card_wave_animation.animationLoop(cardWaveForward, cardWaveBackward)
    }

    private fun listenFloatActionButton() {
        view.main_listen_button.setOnClickListener {
            toggleIconState()
        }
    }

    private fun toggleIconState() {
        val vector = FabAnimatedIcon()
        vector?.start()
        isClicked = !isClicked
    }

    private fun FabAnimatedIcon(): AnimatedVectorDrawableCompat? {
        val fabButtonIcon by lazy {
            AnimatedVectorDrawableCompat.create(
                context,
                if (isClicked) R.drawable.ic_play_stop else R.drawable.ic_stop_play
            ).apply {
                this?.setBounds(0, 0, 60, 60)
            }
        }
        view.main_listen_button.setCompoundDrawables(fabButtonIcon, null, null, null)
        return fabButtonIcon
    }

    fun startItemAnimation(){
        setWaveAnimationToCard()
        listenFloatActionButton()
    }
}