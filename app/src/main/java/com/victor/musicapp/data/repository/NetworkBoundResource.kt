package com.victor.musicapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.victor.musicapp.data.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<Response, ViewState>(
    isNetWorkRequested: Boolean = false,
    isLoadingRequested: Boolean = false
) {

    private val result = MediatorLiveData<DataState<ViewState>>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var job: CompletableJob

    init {
        if (isLoadingRequested) {
            setResultValue(dataState = DataState.load(true))
        }


        setJob(initJob())

        if (isNetWorkRequested) {

            coroutineScope.launch {
                delay(1000)

                withContext(Main) {
                    result.mediateApiData()
                }

            }

        } else {
            coroutineScope.launch {
                loadCachedData()
            }
        }
    }

    private fun MediatorLiveData<DataState<ViewState>>.mediateApiData() {
        this.addSource(responseCall()) { response ->
            this.removeSource(responseCall())

            coroutineScope.launch {
                handleApiResponse(response)
            }

        }
    }

    private fun initJob(): Job {
        job = Job()
        job.invokeOnCompletion { throwable ->
            if (job.isCancelled) {

                throwable?.let {
                    onErrorResponse(it.message!!)
                } ?: onErrorResponse("Network Job Cancellation: Unknown Error")

            }
        }

        coroutineScope = CoroutineScope(IO + job)

        return job
    }

    private suspend fun handleApiResponse(response: GenericApiResponse<Response>) {
        when (response) {
            is ApiSuccessResponse -> handleApiSuccessResponse(response)
            is ApiEmptyResponse -> onErrorResponse("http 201 error")
            is ApiErrorResponse -> onErrorResponse(response.errorMessage)

        }
    }

    protected fun onErrorResponse(message: String) {
        onCompleteResponse(dataState = DataState.error(message))
    }

    protected fun onCompleteResponse(dataState: DataState<ViewState>) {
        GlobalScope.launch(Main) {
            job.complete()
            setResultValue(dataState)
        }
    }

    private fun setResultValue(dataState: DataState<ViewState>) {
        result.value = dataState
    }

    abstract fun setJob(job: Job)
    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Response>)
    abstract fun responseCall(): LiveData<GenericApiResponse<Response>>
    abstract suspend fun loadCachedData()
    val asLiveData = result as LiveData<DataState<ViewState>>

}