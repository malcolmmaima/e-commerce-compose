package com.malcolmmaima.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malcolmmaima.database.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<CartItem>)

    @Query("SELECT * FROM cartitem")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cartitem WHERE id=:id")
    fun getCartItem(id: String): Flow<CartItem>

    @Query("DELETE FROM cartitem WHERE id=:id")
    suspend fun deleteCartItem(id: String)

    @Query("DELETE FROM cartitem")
    suspend fun deleteAllCartItems()

    @Query("UPDATE cartitem SET selectedQuantity = :selectedQuantity WHERE id = :id")
    suspend fun updateCartItem(id: String, selectedQuantity: String)
}
