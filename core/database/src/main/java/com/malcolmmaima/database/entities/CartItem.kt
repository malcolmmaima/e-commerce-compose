package com.malcolmmaima.database.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Keep
data class CartItem(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val imageLocation: String,
    var quantity: Int,
    var selectedQuantity: Int = 1
)
