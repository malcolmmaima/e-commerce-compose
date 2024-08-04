package com.shop.features.networking.util

import okhttp3.ResponseBody

//Handle API Success and Error responses
open class APIResource<out T> {
    data class Success<out T>(val value: T) : APIResource<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : APIResource<Nothing>()
    object Loading : APIResource<Nothing>()
}