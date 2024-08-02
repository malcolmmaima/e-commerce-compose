package com.shop.utils.extensions

fun formatCurrency(currencySymbol: String, price: Double): String {
    val priceString = price.toString().removeSuffix(".0")
    return "$currencySymbol$priceString"
}
