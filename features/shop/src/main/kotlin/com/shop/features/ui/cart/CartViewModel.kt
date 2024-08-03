package com.shop.features.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malcolmmaima.database.entities.CartItem
import com.malcolmmaima.database.repository.CartDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartDatabaseRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.getCartItemsFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartRepository.updateQuantity(cartItem.copy(selectedQuantity = newQuantity))
            }
        }
    }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartRepository.deleteCartItem(cartItem)
            }
        }
    }
}



