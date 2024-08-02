package com.shop.utils.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * annotation which simplifies Preview implementation of feature screens
 * **/
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class UIModePreviews
