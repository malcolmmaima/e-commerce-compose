package com.shop.core.design.ds

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 *  @property [modifier] compose modifier
 *  @property [animationId] lottie animation resource id
 *  @property [contentScale] animation content scale value
 *
 * **/
@Composable
fun InfiniteLottieAnimation(
    modifier: Modifier = Modifier,
    @RawRes animationId: Int,
    contentScale: ContentScale = ContentScale.Fit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationId)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
        contentScale = contentScale
    )
}
