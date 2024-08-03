package com.malcolmmaima.database.repository

import com.malcolmmaima.database.dao.CartDao
import com.malcolmmaima.database.entities.CartItem
import com.shop.features.networking.data.ProductItemResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CartDatabaseRepository @Inject constructor(private val cartDao: CartDao) {
    suspend fun addToCart(product: ProductItemResponse, selectedQuantity: Int) {
        val cartItem = CartItem(
            id = product.id.toString(),
            name = product.name,
            price = product.price,
            imageLocation = product.imageLocation,
            quantity = product.quantity,
            selectedQuantity = selectedQuantity
        )
        cartDao.insert(cartItem)
    }

    fun getCartItemFlow(productId: String): Flow<CartItem?> {
        return cartDao.getCartItem(productId)
    }

    suspend fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    suspend fun deleteCartItem(id: String) {
        cartDao.deleteCartItem(id)
    }

    suspend fun deleteAllCartItems() {
        cartDao.deleteAllCartItems()
    }
}
