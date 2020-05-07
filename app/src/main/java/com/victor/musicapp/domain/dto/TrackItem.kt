package com.victor.musicapp.domain.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.victor.musicapp.domain.dto.Album
import com.victor.musicapp.domain.dto.Artist
import kotlinx.android.parcel.Parcelize

/**
 * @see Keep Denotes that the annotated element should not be removed
 * when the code is minified at build time.
 * This is typically used on methods and classes that are accessed only via reflection
 * so a compiler may think that the code is unused.
 *
 * @see Parcelize  is an optimised serialisation format for Android which is designed to enable
 * us to transfer data between processes.
 *
 * */


@Keep
@Parcelize
data class TrackItem(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("album")
    @Expose
    val album: Album,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>,

    @SerializedName("duration_ms")
    @Expose
    val duration_ms: Float,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("preview_url")
    @Expose
    val preview_url: String
) : Parcelable