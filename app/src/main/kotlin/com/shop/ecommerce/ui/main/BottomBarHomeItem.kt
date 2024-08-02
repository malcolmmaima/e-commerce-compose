package com.shop.ecommerce.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.shop.app.R

enum class BottomBarHomeItem(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    SHOP(
        title = R.string.shop,
        icon = Icons.Filled.AccountCircle
    ),
    SETTINGS(
        title = R.string.bottom_menu_settings,
        icon = Icons.Filled.Settings
    );
}