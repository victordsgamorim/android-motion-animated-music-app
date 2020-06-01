package com.victor.musicapp.presenter.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import javax.inject.Inject


class GlideImageLoader @Inject constructor(private val requestManager: RequestManager) {

    companion object {
        fun convertImageUrlToBitmap(context: Context, url: String?): Bitmap = Glide
            .with(context)
            .asBitmap()
            .load(url)
            .into(100, 100)
            .get()
    }

    fun setUrlToImage(url: String, imageView: ImageView) {
        requestManager.load(url)
            .transition(withCrossFade())
            .into(imageView)
    }



}

