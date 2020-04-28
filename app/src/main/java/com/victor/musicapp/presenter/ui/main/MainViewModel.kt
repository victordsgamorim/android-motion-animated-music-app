package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.musicapp.domain.model.Band
import com.victor.musicapp.data.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getBandList(): LiveData<List<Band>> {
        return repository.getBandList()
    }

    fun getBandSize(): LiveData<Int> {
        return repository.getBandSize()
    }
}