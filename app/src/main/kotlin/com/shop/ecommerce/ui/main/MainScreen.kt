package com.shop.ecommerce.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.shop.ecommerce.ui.NavGraphs
import com.shop.ecommerce.ui.appCurrentDestinationAsState
import com.shop.ecommerce.ui.startAppDestination

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navHostEngine = rememberAnimatedNavHostEngine()

    // fetch state updates and set top level ui adjustments for screen destinations.
    val destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    DestinationsNavHost(
        navController = navController,
        engine = navHostEngine,
        navGraph = NavGraphs.root,
        startRoute = NavGraphs.root.startRoute,
    )
}
