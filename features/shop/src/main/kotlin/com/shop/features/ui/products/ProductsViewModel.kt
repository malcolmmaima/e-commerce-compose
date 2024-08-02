package com.shop.features.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductItemResponse>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductItemResponse>>> get() = _uiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = repository.getProducts()) {
                is APIResource.Success -> {
                    _uiState.value = UiState.Success(
                        result.value.map { apiProduct ->
                            ProductItemResponse(
                                id = apiProduct.id,
                                name = apiProduct.name,
                                currencySymbol = apiProduct.currencySymbol,
                                currencyCode = apiProduct.currencyCode,
                                description = apiProduct.description,
                                imageLocation = apiProduct.imageLocation,
                                price = apiProduct.price,
                                quantity = apiProduct.quantity,
                                status = apiProduct.status
                            )
                        }
                    )
                }
                is APIResource.Error -> {
                    _uiState.value = UiState.Error(
                        mapErrorToMessage(result.errorCode)
                    )
                }

                APIResource.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    private fun mapErrorToMessage(errorCode: Int?): String {
        return when (errorCode) {
            404 -> "Not found"
            500 -> "Server error"
            else -> "Something went wrong"
        }
    }

}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}


