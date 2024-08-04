package com.shop.features

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.malcolmmaima.database.repository.CartDatabaseRepository
import com.shop.features.networking.data.ProductItemResponse
import com.shop.features.networking.repository.ProductRepository
import com.shop.features.networking.util.APIResource
import com.shop.features.ui.products.ProductsViewModel
import com.shop.features.ui.products.UiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductsViewModel
    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val cartRepository: CartDatabaseRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { productRepository.getProducts() } returns APIResource.Success(emptyList())
        coEvery { cartRepository.getCartItemFlow(any()) } returns flowOf(null)

        viewModel = ProductsViewModel(productRepository, cartRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts should update uiState with products on success`() = runTest {
        val products = listOf(
            ProductItemResponse(id = 1, name = "Test Product", price = 100.0, quantity = 10, imageLocation = "", currencySymbol = "$", currencyCode = "USD", description = "Test Description", status = "Available")
        )
        coEvery { productRepository.getProducts() } returns APIResource.Success(products)

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Success)
        assertEquals(products, uiState.data)
    }

    @Test
    fun `fetchProducts should update uiState with error message on failure`() = runTest {
        coEvery { productRepository.getProducts() } returns APIResource.Error(isNetworkError = false, errorCode = 404, errorBody = null)

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Error)
        assertEquals("Resource not found", uiState.message)
    }

    @Test
    fun `fetchProducts should update uiState to Loading while fetching`() = runTest {
        coEvery { productRepository.getProducts() } returns APIResource.Loading

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Loading)
    }

    @Test
    fun `fetchProducts should handle network error correctly`() = runTest {
        coEvery { productRepository.getProducts() } returns APIResource.Error(isNetworkError = true, errorCode = null, errorBody = null)

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Error)
        assertEquals("Something went wrong", uiState.message)
    }

    @Test
    fun `fetchProducts should handle empty product list gracefully`() = runTest {
        coEvery { productRepository.getProducts() } returns APIResource.Success(emptyList())

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Success)
        assertTrue(uiState.data.isEmpty())
    }

    @Test
    fun `fetchProducts should handle invalid response`() = runTest {
        coEvery { productRepository.getProducts() } returns APIResource.Error(isNetworkError = false, errorCode = 500, errorBody = ResponseBody.create(null, "Server error occurred"))

        viewModel.fetchProducts()
        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is UiState.Error)
        assertEquals("Server error", uiState.message)
    }
}

