package com.victor.musicapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.victor.musicapp.data.api.SpotifyService
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.model.Band
import com.victor.musicapp.data.util.ApiEmptyResponse
import com.victor.musicapp.data.util.ApiErrorResponse
import com.victor.musicapp.data.util.ApiSuccessResponse
import com.victor.musicapp.data.util.SpotifyConstants
import com.victor.musicapp.data.util.SpotifyConstants.ALBUM_LIMIT
import com.victor.musicapp.data.util.SpotifyConstants.AUTH_HEADER_MAP
import com.victor.musicapp.data.util.SpotifyConstants.SPOTIFY_ARTIST
import com.victor.musicapp.data.util.SpotifyConstants.SPOTIFY_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val service: SpotifyService) {

    private fun createBand(): List<Band> {
        val bands = mutableListOf<Band>()
        bands.add(Band(1, "Fresno"))
        bands.add(Band(2, "NxZero"))
        bands.add(Band(3, "Pearl Jam"))
        bands.add(Band(4, "Linkin Park"))
        return bands
    }

    fun getBandList(): LiveData<List<Band>> {
        val list = MutableLiveData<List<Band>>()
        list.value = createBand()
        return list
    }

    fun getTrack(): LiveData<SpotifyApiResponse> {
        val result = MediatorLiveData<SpotifyApiResponse>()

        CoroutineScope(IO).launch {
            withContext(Main) {
                result.addSource(
                    service.getTrack(
                        AUTH_HEADER_MAP,
                        SPOTIFY_ARTIST,
                        SPOTIFY_TYPE,
                        ALBUM_LIMIT
                    )
                ) { response ->
                    when (response) {
                        is ApiSuccessResponse -> {
                            result.value = response.body
                        }
                        is ApiEmptyResponse -> {
                            Log.e("ApiEmptyResponse", "Error 201 response!")
                        }
                        is ApiErrorResponse -> {
                            Log.e("ApiErrorResponse", "Error ${response.errorMessage}")
                        }
                    }

                }
            }
        }

        return result
    }
}