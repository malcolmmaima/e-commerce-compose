package com.shop.features.ui.products

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shop.features.R
import com.shop.features.networking.data.ProductItemResponse
import com.shop.utils.extensions.formatCurrency

@Composable
fun ProductDetailBottomSheet(
    product: ProductItemResponse,
    isInCart: Boolean,
    onAddToCartClicked: (Int) -> Unit,
    onDismiss: (String) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = product.imageLocation,
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = formatCurrency(product.currencySymbol, product.price),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (quantity > 1) quantity -= 1
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isInCart) Color.Gray else androidx.compose.material.MaterialTheme.colors.primary,
                    contentColor = Color.White
                ),
                enabled = !isInCart && quantity > 1
            ) {
                Text(text = "-", fontSize = 18.sp)
            }
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = {
                    quantity += 1
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isInCart) Color.Gray else androidx.compose.material.MaterialTheme.colors.primary,
                    contentColor = Color.White
                ),
                enabled = !isInCart
            ) {
                Text(text = "+", fontSize = 18.sp)
            }
        }

        Button(
            onClick = {
                if (!isInCart) {
                    onAddToCartClicked(quantity)
                    Toast.makeText(
                        context,
                        "${product.name} added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                    quantity = 1
                    onDismiss(product.id.toString())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isInCart) Color.Gray else androidx.compose.material.MaterialTheme.colors.primary,
                contentColor = Color.White
            ),
            enabled = !isInCart
        ) {
            Text(text = stringResource(id = R.string.add_to_cart))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    R.string.buy_now,
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = androidx.compose.material.MaterialTheme.colors.secondary,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(id = R.string.buy_now))
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
