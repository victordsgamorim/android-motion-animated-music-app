package com.victor.musicapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victor.musicapp.model.Band
import javax.inject.Inject

class MainRepository @Inject constructor() {

    fun getBandList(): LiveData<List<Band>> {
        val list = MutableLiveData<List<Band>>()

        val bands = mutableListOf<Band>()
        bands.add(Band(1, "Fresno"))
        bands.add(Band(2, "Rua"))

        list.value = bands

        return list
    }
}