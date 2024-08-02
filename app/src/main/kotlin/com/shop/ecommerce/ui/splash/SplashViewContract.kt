package com.shop.ecommerce.ui.splash

object SplashViewContract {
    sealed interface Effect {
        data object Completed : Effect
    }
}
