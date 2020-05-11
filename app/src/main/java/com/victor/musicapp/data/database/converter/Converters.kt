package com.victor.musicapp.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victor.musicapp.domain.dto.Album
import com.victor.musicapp.domain.dto.AlbumImage
import com.victor.musicapp.domain.dto.Artist
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.domain.dto.artist.ArtistExternalUrl
import com.victor.musicapp.domain.dto.artist.ArtistFollowers
import com.victor.musicapp.domain.dto.artist.ArtistImages
import java.util.*

class TrackItemConverter {
    @TypeConverter
    fun trackItemToString(tracks: List<TrackItem>): String {
        return Gson().toJson(tracks)
    }

    @TypeConverter
    fun stringToTrackItem(data: String?): List<TrackItem> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<TrackItem>>() {}.type
        return Gson().fromJson(data, listType)
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

//class ArtistsConverter {
//    @TypeConverter
//    fun artistToString(artists: List<Artist>): String {
//        return Gson().toJson(artists)
//    }
//
//    @TypeConverter
//    fun stringToArtists(data: String?): List<Artist> {
//        if (data == null) {
//            return Collections.emptyList();
//        }
//        val listType = object : TypeToken<List<Artist>>() {}.type
//        return Gson().fromJson(data, listType);
//    }
//}

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

class ArtistExternalUrlConverter {

    @TypeConverter
    fun externalUrlToString(externalObject: ArtistExternalUrl): String {
        return Gson().toJson(externalObject)
    }

    @TypeConverter
    fun stringToArtistExternalUrl(data: String?): ArtistExternalUrl {
        return Gson().fromJson(data, ArtistExternalUrl::class.java)
    }

}

class ArtistFollowersConverter {

    @TypeConverter
    fun artistFollowersToString(followers: ArtistFollowers): String {
        return Gson().toJson(followers)
    }

    @TypeConverter
    fun stringToArtistFollowers(data: String?): ArtistFollowers {
        return Gson().fromJson(data, ArtistFollowers::class.java)
    }

}

class ArtistImagesConverter {

    @TypeConverter
    fun artistImageToString(images: List<ArtistImages>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun stringToArtistImage(data: String?): List<ArtistImages> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<ArtistImages>>() {}.type
        return Gson().fromJson(data, listType)
    }

}
