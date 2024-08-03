package com.shop.features.ui.cart

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.malcolmmaima.database.entities.CartItem
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    val viewModel: CartViewModel = hiltViewModel()
    val cartItems by viewModel.cartItems.collectAsState()
    val context = LocalContext.current

    val totalItems = cartItems.sumOf { it.selectedQuantity }
    val totalPrice = cartItems.sumOf { it.selectedQuantity * it.price }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.shop.utils.R.raw.shopping_cart))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(top = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Total Items: $totalItems",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Total Price: $${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    if (totalItems > 0) {
                        Text(
                            text = "Your cart is ready for checkout!",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            LazyColumn {
                items(cartItems) { cartItem ->
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        CartItemRow(
                            cartItem = cartItem,
                            onIncreaseQuantity = {
                                if (cartItem.selectedQuantity < cartItem.quantity)
                                    viewModel.updateQuantity(cartItem, cartItem.selectedQuantity + 1)
                            },
                            onDecreaseQuantity = {
                                if (cartItem.selectedQuantity > 1) {
                                    viewModel.updateQuantity(cartItem, cartItem.selectedQuantity - 1)
                                }
                            },
                            onDelete = {
                                viewModel.deleteItem(cartItem)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Checkout clicked",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Icon(Icons.Default.ShoppingCartCheckout, contentDescription = "Checkout")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Checkout", color = Color.White)
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = cartItem.name, style = MaterialTheme.typography.body1)
            Text(
                text = "Price: $${"%.2f".format(cartItem.price)}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Total: $${"%.2f".format(cartItem.selectedQuantity * cartItem.price)}",
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecreaseQuantity) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease quantity")
            }
            Text(text = cartItem.selectedQuantity.toString(), style = MaterialTheme.typography.body1)
            IconButton(onClick = onIncreaseQuantity) {
                Icon(Icons.Default.Add, contentDescription = "Increase quantity")
            }
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete item")
        }
    }
}
