package com.shop.features.ui.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    onAddToCartClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
    ) {
        item {
            AsyncImage(
                model = product.imageLocation,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        }
        item {
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
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onAddToCartClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(text = stringResource(id = R.string.add_to_cart))
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
