package com.shop.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colors = colorScheme,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}

val ShopColors: Colors
    @Composable get() = androidx.compose.material.MaterialTheme.colors