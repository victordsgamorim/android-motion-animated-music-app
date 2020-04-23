package com.victor.musicapp.util

import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

fun ImageView.setAnimationToDrawable(animatedDrawable: AnimatedVectorDrawableCompat?) {
    animatedDrawable?.let {
        this.setImageDrawable(animatedDrawable)
        this.maxWidth = 24
        this.maxHeight = 24
    } ?: throw NullPointerException("Animated Drawable: $animatedDrawable is null")

}