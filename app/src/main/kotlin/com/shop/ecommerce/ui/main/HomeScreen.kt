package com.shop.ecommerce.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shop.core.design.theme.ShopTheme
import com.shop.features.ui.cart.CartScreen
import com.shop.features.ui.products.ProductsScreen
import com.shop.features.ui.settings.SettingsScreen
import com.shop.utils.mvi.EffectCollector
import com.shop.utils.navigation.FadeInOutAnimation
import com.shop.utils.navigation.MockDestinationsNavigator
import com.shop.utils.preview.UIModePreviews

@Destination(style = FadeInOutAnimation::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    val (currentBottomTab, setCurrentBottomTab) = rememberSaveable {
        mutableStateOf(BottomBarHomeItem.SHOP)
    }

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                bottomTab = currentBottomTab,
                setCurrentBottomTab = setCurrentBottomTab
            )
        },
        content = { paddingValues ->
            val modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()

            when (currentBottomTab) {
                BottomBarHomeItem.SHOP -> ProductsScreen(
                    modifier = modifier,
                    navigator = navigator
                )
                BottomBarHomeItem.CART -> CartScreen(
                    modifier = modifier,
                    navigator = navigator
                )
                BottomBarHomeItem.SETTINGS -> SettingsScreen(
                    modifier = modifier,
                    navigator = navigator
                )
            }
        }
    )
}

@Composable
private fun HomeBottomNavigation(
    bottomTab: BottomBarHomeItem,
    setCurrentBottomTab: (BottomBarHomeItem) -> Unit
) {
    val pages = BottomBarHomeItem.entries.toTypedArray()
    val bottomBarHeight = 100.dp

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomBarHeight)
    ) {
        pages.forEach { page ->
            val selected = page == bottomTab
            val selectedLabelColor = if (selected) {
                MaterialTheme.colors.primary
            } else {
                Gray
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = rememberVectorPainter(image = page.icon),
                        contentDescription = stringResource(page.title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(page.title),
                        color = selectedLabelColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selected = selected,
                onClick = {
                    setCurrentBottomTab.invoke(page)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Gray,
                alwaysShowLabel = true,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@UIModePreviews
@Composable
private fun HomeScreenPreview() {
    ShopTheme {
        HomeScreen(
            navigator = MockDestinationsNavigator()
        )
    }
}
