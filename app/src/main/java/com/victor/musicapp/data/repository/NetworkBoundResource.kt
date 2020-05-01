package com.victor.musicapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.victor.musicapp.data.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<Response, ViewState> {

    private val result = MediatorLiveData<DataState<ViewState>>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var job: CompletableJob

    init {

        setJob(initJob())

        coroutineScope.launch {
            delay(1000)

            withContext(Main) {
                result.addSource(responseCall()) { response ->
                    result.removeSource(responseCall())

                    coroutineScope.launch {
                        handleApiResponse(response)
                    }

                }
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
    val asLiveData = result as LiveData<DataState<ViewState>>

}