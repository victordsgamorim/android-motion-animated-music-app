package com.victor.musicapp.presenter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.presenter.ui.main.state.MainViewState

abstract class BaseViewModel<StateEvent, ViewState>() : ViewModel() {

    private val _stateEvent = MutableLiveData<StateEvent>()
    protected val _viewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> =
        Transformations.switchMap(_stateEvent) { stateEvent ->
            handleStateEventResponse(stateEvent)
        }

    fun addStateEvent(event: StateEvent) {
        _stateEvent.value = event
    }

    /**
    add new event in order to act as a trigger to start
    the search of some data from api or internal database*/
    protected fun getCurrentViewState(): ViewState {
        return _viewState.value?.let { it } ?: initViewState()
    }


    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    abstract fun handleStateEventResponse(stateEvent: StateEvent): LiveData<DataState<MainViewState>>
    abstract fun initViewState(): ViewState

    /**coroutine job cancellation*/
    abstract fun cancelJob()


}