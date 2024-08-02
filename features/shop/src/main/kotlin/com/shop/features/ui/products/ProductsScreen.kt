package com.shop.features.ui.products

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shop.utils.preview.UIModePreviews
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import com.shop.features.networking.data.ProductItemResponse
import com.shop.utils.extensions.formatCurrency
import com.shop.utils.navigation.MockDestinationsNavigator
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(modifier: Modifier, navigator: DestinationsNavigator) {
    val viewModel: ProductsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

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

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = modifier
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
                                ProductItem(product, navigator)
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
        }
    )
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductItem(product: ProductItemResponse, navigator: DestinationsNavigator) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.shop.utils.R.raw.loading))

    val coroutineScope = rememberCoroutineScope()
    val showModalBottomSheet = remember { mutableStateOf(false) }

    if (showModalBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showModalBottomSheet.value = false }
        ) {
            QuantityScreen(
                quantity = product.quantity,
                onQuantityClicked = {
                    showModalBottomSheet.value = false
                },
                onDismiss = { showModalBottomSheet.value = false }
            )
        }
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        onClick = {
            coroutineScope.launch {
                showModalBottomSheet.value = true
            }
        }
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
                            modifier = Modifier.size(100.dp) // Increased size for the image
                        )
                    },
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(100.dp) // Increased size for the image
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuantityScreen(
    modifier: Modifier = Modifier,
    quantity: Int,
    onQuantityClicked: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetsState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    val list by remember {
        mutableStateOf((1..quantity).toList())
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(list.size) { quantity ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            onQuantityClicked(quantity)
                            scope
                                .launch {
                                    sheetsState.hide()
                                }
                                .invokeOnCompletion {
                                    onDismiss()
                                }
                        }
                ) {
                    Text(text = quantity.toString())
                }
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
