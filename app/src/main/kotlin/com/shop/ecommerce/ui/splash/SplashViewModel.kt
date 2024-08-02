package com.shop.ecommerce.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.ecommerce.ui.splash.SplashViewContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _effect by lazy { Channel<Effect>() }
    val effect: Flow<Effect> by lazy { _effect.receiveAsFlow() }

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            delay(4000)
            _effect.send(Effect.Completed)
        }
    }
}
