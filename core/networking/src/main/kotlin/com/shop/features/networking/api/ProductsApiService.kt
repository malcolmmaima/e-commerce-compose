package com.shop.features.networking.api

import com.shop.features.networking.data.ProductItemResponse
import retrofit2.http.GET

interface ProductsApiService {
    @GET("productBundles")
    suspend fun getProducts() : List<ProductItemResponse>
}
