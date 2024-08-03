package com.malcolmmaima.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malcolmmaima.database.entities.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<CartItem>)

    @Query("SELECT * FROM cartitem")
    fun getAllCartItems(): List<CartItem>

    @Query("DELETE FROM cartitem WHERE id=:id")
    suspend fun deleteCartItem(id: String)

    @Query("DELETE FROM cartitem")
    suspend fun deleteAllCartItems()
}
