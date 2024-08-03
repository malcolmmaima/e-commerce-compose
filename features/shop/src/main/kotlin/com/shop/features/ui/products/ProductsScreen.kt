package com.shop.features.ui.products

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shop.features.networking.data.ProductItemResponse
import com.shop.utils.extensions.formatCurrency
import kotlinx.coroutines.launch
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.ui.Alignment
import com.shop.utils.navigation.MockDestinationsNavigator
import com.shop.utils.preview.UIModePreviews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsScreen(modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val viewModel: ProductsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val refreshing = remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing.value, onRefresh = {
        refreshing.value = true
        viewModel.refreshProducts()
        refreshing.value = false
    })

    var selectedProduct by remember { mutableStateOf<ProductItemResponse?>(null) }
    var isInCart by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    // filtered list based on search query
    val filteredProducts = when (val state = uiState) {
        is UiState.Success -> {
            if (searchQuery.isEmpty()) {
                state.data
            } else {
                state.data.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }
        }
        else -> emptyList()
    }

    @Composable
    fun SearchBar(
        query: String,
        onQueryChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val borderRadius = 40.dp
        val borderColor = Color.Gray

        TextField(
            value = query,
            onValueChange = { newQuery -> onQueryChange(newQuery) },
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(borderRadius))
                .border(1.dp, borderColor, RoundedCornerShape(borderRadius)),
            placeholder = { Text("Search item...") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                focusedBorderColor = borderColor,
            )
        )
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 32.dp)
                        .fillMaxSize()
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { newQuery -> searchQuery = newQuery }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    when (uiState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is UiState.Success -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = paddingValues,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(filteredProducts) { product ->
                                    ProductItem(product, navigator) {
                                        selectedProduct = it
                                        coroutineScope.launch {
                                            viewModel.isProductInCart(product.id.toString()).collect { inCart ->
                                                isInCart = inCart
                                                bottomSheetState.show()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        is UiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = (uiState as UiState.Error).message ?: "Unknown error", color = Color.Red)
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = refreshing.value,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    )

    selectedProduct?.let { product ->
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxHeight(0.9f),
            sheetContent = {
                ProductDetailBottomSheet(
                    product = product,
                    isInCart = isInCart,
                    onAddToCartClicked = { selectedQuantity ->
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                viewModel.addToCart(product, selectedQuantity)
                            }
                            bottomSheetState.hide()
                        }
                    },
                    onDismiss = { productId ->
                        coroutineScope.launch { bottomSheetState.hide() }
                    }
                )
            },
            sheetState = bottomSheetState,
            content = {  }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductItem(product: ProductItemResponse, navigator: DestinationsNavigator, onProductClick: (ProductItemResponse) -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.shop.utils.R.raw.loading))

    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .fillMaxWidth()
            .clickable { onProductClick(product) },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageLocation)
                        .crossfade(true)
                        .build(),
                    loading = {
                        LottieAnimation(
                            composition = composition,
                            modifier = Modifier.size(50.dp)
                        )
                    },
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    fontStyle = MaterialTheme.typography.subtitle1.fontStyle
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatCurrency(product.currencySymbol, product.price),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@UIModePreviews
@Composable
fun ProductScreenPreview() {
    ProductsScreen(
        modifier = Modifier,
        navigator = MockDestinationsNavigator()
    )
}
