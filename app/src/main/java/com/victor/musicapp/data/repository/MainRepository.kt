package com.victor.musicapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victor.musicapp.domain.model.Band
import javax.inject.Inject

class MainRepository @Inject constructor() {

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

    fun getBandSize(): LiveData<Int> {
        val list = MutableLiveData<Int>()
        list.value = createBand().size
        return list
    }
}