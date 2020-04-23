package com.victor.musicapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.victor.musicapp.util.setAnimationToDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.end_motion_extended_card_info.*
import kotlinx.android.synthetic.main.scrolling_bottom_details.*


class MainActivity : AppCompatActivity() {

    private var isClicked = true

    private val topLeftForward by lazy {
        AnimatedVectorDrawableCompat.create(this, R.drawable.wave_forward)
    }

    private val topLeftReverse by lazy {
        AnimatedVectorDrawableCompat.create(this, R.drawable.wave_reverse)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fluidTopLeft.setAnimationToDrawable(topLeftForward)

        main_listen_button.setOnClickListener {
            toggleIconState()
        }


        /**callbacks*/
        topLeftForward?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                fluidTopLeft.setImageDrawable(topLeftReverse)
                topLeftReverse?.start()
            }
        })

        topLeftReverse?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                fluidTopLeft.setImageDrawable(topLeftForward)
                topLeftForward?.start()
            }
        })

        topLeftForward?.start()
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
