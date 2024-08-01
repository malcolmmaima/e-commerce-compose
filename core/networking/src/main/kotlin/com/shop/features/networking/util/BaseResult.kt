package com.shop.features.networking.util

sealed class BaseResult<T : Any, E : Any> {
    class Loading<T : Any, E : Any> : BaseResult<T, E>()
    class Failure<T : Any, E : Any>(val error: E) : BaseResult<T, E>()
    class Success<T : Any, E : Any>(val data: T) : BaseResult<T, E>()
}