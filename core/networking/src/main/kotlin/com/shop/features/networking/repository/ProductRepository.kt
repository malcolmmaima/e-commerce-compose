package com.shop.features.networking.repository

import com.shop.features.networking.api.ProductApiService
import com.shop.features.networking.util.BaseRepo
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) : BaseRepo() {

    suspend fun getProducts() = safeApiCall {
        apiService.getProducts()
    }
}







