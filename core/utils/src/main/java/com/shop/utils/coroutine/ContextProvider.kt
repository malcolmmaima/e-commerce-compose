package com.shop.utils.coroutine

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

interface ContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}

class ContextProviderImpl : ContextProvider {
    override val main: CoroutineContext by lazy { Dispatchers.Main }
    override val io: CoroutineContext by lazy { Dispatchers.IO }
}
