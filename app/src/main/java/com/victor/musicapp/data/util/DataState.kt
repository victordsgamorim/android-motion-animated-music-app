package com.victor.musicapp.data.util

class DataState<T>(val data: T? = null, val error: String? = null, val loading: Boolean = false) {

    companion object {

        fun <T> error(message: String): DataState<T> {
            return DataState(error = message)
        }

        fun <T> data(data: T): DataState<T> {
            return DataState(data = data)
        }

        fun <T> load(loading: Boolean): DataState<T> {
            return DataState(loading = loading)
        }
    }
}