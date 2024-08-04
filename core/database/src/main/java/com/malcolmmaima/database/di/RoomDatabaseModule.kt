package com.malcolmmaima.database.di

import android.content.Context
import androidx.room.Room
import com.malcolmmaima.database.ShopDatabase
import com.malcolmmaima.database.dao.CartDao
import com.malcolmmaima.database.migrations.ShopDatabaseMigrations
import com.malcolmmaima.database.repository.CartDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideShopDatabase(
        @ApplicationContext context: Context
    ): ShopDatabase = Room.databaseBuilder(
        context = context,
        name = "shopdatabase.db",
        klass = ShopDatabase::class.java
    ).addMigrations(ShopDatabaseMigrations.cart_migrations_1_2)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCartDao(
        shopDatabase: ShopDatabase
    ): CartDao = shopDatabase.cartDao()

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao
    ): CartDatabaseRepository = CartDatabaseRepository(cartDao)
}
