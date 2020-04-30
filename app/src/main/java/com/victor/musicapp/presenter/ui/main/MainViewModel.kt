package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.model.Band
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getBandList(): LiveData<List<Band>> {
        return repository.getBandList()
    }

    fun getTrack(): LiveData<SpotifyApiResponse> {
        return repository.getTrack()
    }
}