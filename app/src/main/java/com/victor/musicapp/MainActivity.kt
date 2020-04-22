package com.victor.musicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play_pause = AnimatedVectorDrawableCompat.create(this, R.drawable.ic_play_pause)

        play_pause?.setBounds(0, 0, 60, 60)
        main_listen_button.setCompoundDrawables(play_pause, null, null, null)

        main_listen_button.setOnClickListener {
            play_pause?.start()
        }

    }
}
