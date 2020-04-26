package com.victor.musicapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.musicapp.model.Band
import com.victor.musicapp.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getBandList(): LiveData<List<Band>> {
        return repository.getBandList()
    }
}