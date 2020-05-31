package com.victor.musicapp.presenter.util

import android.content.Context
import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private suspend fun convertImageUrlToBitmap(context: Context, url: String?): Bitmap = Glide
    .with(context)
    .asBitmap()
    .load(url)
    .into(100, 100)
    .get()

suspend fun getDominantColourFromImage(context: Context, url: String?, background: (Int) -> Unit) {

    val image = convertImageUrlToBitmap(context, url)

    withContext(Dispatchers.Main) {
        Palette.from(image).generate() {
            val lightMutedSwatch = it?.vibrantSwatch
            lightMutedSwatch?.rgb?.let { color ->
                background(color)
            }
        }
    }
}
