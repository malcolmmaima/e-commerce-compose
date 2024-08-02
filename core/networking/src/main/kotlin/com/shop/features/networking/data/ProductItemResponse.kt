package com.shop.features.networking.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ProductItemResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val currencyCode: String,
    val currencySymbol: String,
    val quantity: Int,
    val imageLocation: String,
    val status: String
) {
    val priceString: String
        get() = "$currencySymbol$price"
}
