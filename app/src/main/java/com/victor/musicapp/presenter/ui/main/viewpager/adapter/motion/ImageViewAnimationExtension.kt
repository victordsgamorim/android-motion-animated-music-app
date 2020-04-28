package com.victor.musicapp.presenter.ui.main.viewpager.adapter.motion

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

fun ImageView.setAnimationToDrawable(animatedDrawable: AnimatedVectorDrawableCompat?) {
    animatedDrawable?.let {
        setImageDrawable(animatedDrawable)
        animatedDrawable.start()
    } ?: throw NullPointerException("Animated Drawable: $animatedDrawable is null")

}

fun ImageView.animationLoop(forward: AnimatedVectorDrawableCompat?, backward: AnimatedVectorDrawableCompat?){
    registerAnimationCallback(forward, backward)
    registerAnimationCallback(backward, forward)
}

private fun ImageView.registerAnimationCallback(
    vector1: AnimatedVectorDrawableCompat?,
    vector2: AnimatedVectorDrawableCompat?
) {
    vector1?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            setAnimationToDrawable(vector2)
        }
    })
}