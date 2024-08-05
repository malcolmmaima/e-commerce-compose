package com.malcolmmaima.database.repository

import com.malcolmmaima.database.dao.CartDao
import com.malcolmmaima.database.entities.CartItem
import com.shop.features.networking.data.ProductItemResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CartDatabaseRepositoryTest {

    private lateinit var cartDao: CartDao
    private lateinit var cartDatabaseRepository: CartDatabaseRepository

    @Before
    fun setUp() {
        cartDao = mockk()
        cartDatabaseRepository = CartDatabaseRepository(cartDao)
    }

    @Test
    fun `addToCart inserts a CartItem into the database`() = runTest {
        val product = ProductItemResponse(
            id = 1,
            name = "Product 1",
            price = 10.0,
            imageLocation = "image.png",
            quantity = 5,
            currencySymbol = "$",
            description = "Description",
            currencyCode = "USD",
            status = "Available"
        )
        val selectedQuantity = 2
        val cartItem = CartItem(
            id = product.id.toString(),
            name = product.name,
            price = product.price,
            imageLocation = product.imageLocation,
            quantity = product.quantity,
            selectedQuantity = selectedQuantity,
            currencySymbol = product.currencySymbol
        )
        coEvery { cartDao.insert(cartItem) } just Runs

        cartDatabaseRepository.addToCart(product, selectedQuantity)

        coVerify { cartDao.insert(cartItem) }
    }

    @Test
    fun `getCartItemFlow returns the correct Flow from dao`() = runTest {
        val productId = "1"
        val cartItem = CartItem(
            id = productId,
            name = "Product 1",
            price = 10.0,
            imageLocation = "image.png",
            quantity = 5,
            selectedQuantity = 2,
            currencySymbol = "$"
        )
        every { cartDao.getCartItem(productId) } returns flowOf(cartItem)

        cartDatabaseRepository.getCartItemFlow(productId).test {
            assertEquals(cartItem, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCartItemsFlow returns the correct Flow from dao`() = runTest {
        val cartItems = listOf(
            CartItem(
                id = "1",
                name = "Product 1",
                price = 10.0,
                imageLocation = "image.png",
                quantity = 5,
                selectedQuantity = 2,
                currencySymbol = "$"
            )
        )
        every { cartDao.getAllCartItems() } returns flowOf(cartItems)

        cartDatabaseRepository.getCartItemsFlow().test {
            assertEquals(cartItems, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateQuantity updates the CartItem in the database`() = runTest {
        val cartItem = CartItem(
            id = "1",
            name = "Product 1",
            price = 10.0,
            imageLocation = "image.png",
            quantity = 5,
            selectedQuantity = 2,
            currencySymbol = "$"
        )
        coEvery { cartDao.updateCartItem(cartItem.id, cartItem.selectedQuantity.toString()) } just Runs

        cartDatabaseRepository.updateQuantity(cartItem)

        coVerify { cartDao.updateCartItem(cartItem.id, cartItem.selectedQuantity.toString()) }
    }

    @Test
    fun `deleteCartItem deletes the CartItem from the database`() = runTest {
        val cartItem = CartItem(
            id = "1",
            name = "Product 1",
            price = 10.0,
            imageLocation = "image.png",
            quantity = 5,
            selectedQuantity = 2,
            currencySymbol = "$"
        )
        coEvery { cartDao.deleteCartItem(cartItem.id) } just Runs

        cartDatabaseRepository.deleteCartItem(cartItem)

        coVerify { cartDao.deleteCartItem(cartItem.id) }
    }

    @Test
    fun `deleteAllCartItems deletes all CartItems from the database`() = runTest {
        coEvery { cartDao.deleteAllCartItems() } just Runs

        cartDatabaseRepository.deleteAllCartItems()

        coVerify { cartDao.deleteAllCartItems() }
    }
}
