package com.victor.musicapp.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victor.musicapp.data.api.response.Album
import com.victor.musicapp.data.api.response.AlbumImage
import com.victor.musicapp.data.api.response.Artist
import com.victor.musicapp.data.api.response.TrackItem
import java.util.*

class TrackItemConverter {
    @TypeConverter
    fun trackItemToString(tracks: List<TrackItem>): String {
        return Gson().toJson(tracks)
    }

    @TypeConverter
    fun stringToTrackItem(data: String?): List<TrackItem> {
        if (data == null) {
            return Collections.emptyList();
        }
        val listType = object : TypeToken<List<TrackItem>>() {}.type
        return Gson().fromJson(data, listType);
    }
}

class AlbumConverter {
    @TypeConverter
    fun albumToString(album: Album): String {
        return Gson().toJson(album)
    }

    @TypeConverter
    fun stringToAlbum(data: String?): Album {
        return Gson().fromJson(data, Album::class.java)
    }
}

class ArtistsConverter {
    @TypeConverter
    fun artistToString(artists: List<Artist>): String {
        return Gson().toJson(artists)
    }

    @TypeConverter
    fun stringToArtists(data: String?): List<Artist> {
        if (data == null) {
            return Collections.emptyList();
        }
        val listType = object : TypeToken<List<Artist>>() {}.type
        return Gson().fromJson(data, listType);
    }
}

class AlbumImageConverter {
    @TypeConverter
    fun albumImageToString(images: List<AlbumImage>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun stringToAlbumImage(data: String?): List<AlbumImage> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<AlbumImage>>() {}.type
        return Gson().fromJson(data, listType)
    }

}
