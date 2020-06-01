package com.victor.musicapp.presenter.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.victor.musicapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun getDominantColourFromImage(
    context: Context,
    url: String?,
    background: (Int) -> Unit
) {

    val image = GlideImageLoader.convertImageUrlToBitmap(context, url)

    withContext(Dispatchers.Main) {
        Palette.from(image).generate() { palette ->
            palette?.vibrantSwatch?.rgb?.let { vibrantSwatch ->
                background(vibrantSwatch)
            }
        }
    }
}

fun setDominantColourToView(context: Context, url: String, view: (Int) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        getDominantColourFromImage(context, url) { colour ->
            view(colour)
        }
    }
}

/**toolbar text colour */
/**true is dark, false is light*/
fun isColourDark(color: Int): Boolean {
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
            color
        )) / 255
    return darkness >= 0.5
}

fun fontColour(colour: Int): Int {
    return if (isColourDark(colour)) {
        Color.WHITE
    } else {
        Color.BLACK
    }
}
