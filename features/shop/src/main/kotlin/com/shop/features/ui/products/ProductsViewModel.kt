package com.shop.features.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malcolmmaima.database.repository.CartDatabaseRepository
import com.shop.features.networking.data.ProductItemResponse
import com.shop.features.networking.repository.ProductRepository
import com.shop.features.networking.util.APIResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartDatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductItemResponse>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductItemResponse>>> = _uiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val response = productRepository.getProducts()) {
                is APIResource.Success -> {
                    UiState.Success(response.value)
                }
                is APIResource.Error -> {
                    UiState.Error(mapErrorToMessage(response.errorCode))
                }

                APIResource.Loading -> {
                    UiState.Loading
                }
            }
        }
    }

    fun refreshProducts() {
        fetchProducts()
    }

    private fun mapErrorToMessage(errorCode: Int?): String {
        return when (errorCode) {
            404 -> "Resource not found"
            500 -> "Server error"
            else -> "Something went wrong"
        }
    }

    fun addToCart(product: ProductItemResponse, selectedQuantity: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(product, selectedQuantity)
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}


