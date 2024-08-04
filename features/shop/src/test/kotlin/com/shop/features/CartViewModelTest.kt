package com.shop.features

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.malcolmmaima.database.entities.CartItem
import com.malcolmmaima.database.repository.CartDatabaseRepository
import com.shop.features.ui.cart.CartViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private val cartRepository: CartDatabaseRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { cartRepository.getCartItemsFlow() } returns MutableStateFlow(emptyList())

        coEvery { cartRepository.updateQuantity(any()) } returns Unit
        coEvery { cartRepository.deleteCartItem(any()) } returns Unit

        viewModel = CartViewModel(cartRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateQuantity should call updateQuantity in repository`() = runTest {
        val cartItem = CartItem(
            id = "1", name = "Test Item",
            selectedQuantity = 1,
            quantity = 10,
            price = 100.0,
            imageLocation = "",
            currencySymbol = "$"
        )
        val newQuantity = 2

        viewModel.updateQuantity(cartItem, newQuantity)

        advanceUntilIdle()

        coVerify { cartRepository.updateQuantity(cartItem.copy(selectedQuantity = newQuantity)) }
    }

    @Test
    fun `deleteItem should call deleteCartItem in repository`() = runTest {
        val cartItem = CartItem(
            id = "1", name = "Test Item",
            selectedQuantity = 1,
            quantity = 10,
            price = 100.0,
            imageLocation = "",
            currencySymbol = "$"
        )

        viewModel.deleteItem(cartItem)

        advanceUntilIdle()

        coVerify { cartRepository.deleteCartItem(cartItem) }
    }

    @Test
    fun `cartItems should emit initial state`() = runTest {
        val initialCartItems = emptyList<CartItem>()
        every { cartRepository.getCartItemsFlow() } returns MutableStateFlow(initialCartItems)

        val cartItems = viewModel.cartItems.first()

        assertEquals(initialCartItems, cartItems)
    }
}

