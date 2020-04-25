package com.victor.musicapp.ui.recyclerview.adapter.motion

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout

object MotionLayoutListener : MotionLayout.TransitionListener {
    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        p1: Int,
        p2: Boolean,
        p3: Float
    ) {

    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, start: Int, end: Int) {

    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        start: Int,
        end: Int,
        progress: Float
    ) {
        Log.e("MotionListener", progress.toString())
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, current: Int) {

    }

}