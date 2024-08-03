package com.malcolmmaima.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malcolmmaima.database.dao.CartDao
import com.malcolmmaima.database.entities.CartItem

@Database(
    version = 1,
    entities = [CartItem::class],
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
