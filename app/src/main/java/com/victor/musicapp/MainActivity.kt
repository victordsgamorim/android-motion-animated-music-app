package com.victor.musicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.victor.musicapp.util.animationLoop
import com.victor.musicapp.util.setAnimationToDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.end_motion_extended_card_info.*


class MainActivity : AppCompatActivity() {

    private var isClicked = true

    private val cardWaveForward by lazy{
        AnimatedVectorDrawableCompat.create(this, R.drawable.animated_card_wave_forward)
    }

    private val cardWaveBackward by lazy{
        AnimatedVectorDrawableCompat.create(this, R.drawable.animated_card_wave_backward)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setWaveAnimationToCard()
        listenFloatActionButton()

    }

    private fun setWaveAnimationToCard() {
        album_card_wave_animation.setAnimationToDrawable(cardWaveForward)
        album_card_wave_animation.animationLoop(cardWaveForward, cardWaveBackward)
    }

    private fun listenFloatActionButton() {
        main_listen_button.setOnClickListener {
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
                this,
                if (isClicked) R.drawable.ic_play_stop else R.drawable.ic_stop_play
            ).apply {
                this?.setBounds(0, 0, 60, 60)
            }
        }
        main_listen_button.setCompoundDrawables(fabButtonIcon, null, null, null)
        return fabButtonIcon
    }
}
