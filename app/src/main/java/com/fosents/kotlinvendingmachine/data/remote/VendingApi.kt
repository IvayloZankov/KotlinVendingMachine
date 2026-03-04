package com.fosents.kotlinvendingmachine.data.remote

import com.fosents.kotlinvendingmachine.data.remote.dto.CoinDto
import com.fosents.kotlinvendingmachine.data.remote.dto.ProductDto
import retrofit2.http.*

private const val GET_PRODUCTS = "getProducts"
private const val DECREASE_PRODUCT = "decreaseProduct"
private const val RESET_PRODUCTS = "resetProducts"
private const val GET_COINS = "getCoins"
private const val RESET_COINS = "resetCoins"
private const val UPDATE_COINS = "updateCoins"

interface VendingApi {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): ApiResponse<ProductDto>

    @FormUrlEncoded
    @POST(DECREASE_PRODUCT)
    suspend fun decreaseProduct(
        @FieldMap params: Map<String, Int>
    ): ApiResponse<ProductDto>

    @GET(RESET_PRODUCTS)
    suspend fun resetProducts(): ApiResponse<ProductDto>

    @GET(GET_COINS)
    suspend fun getCoins(): ApiResponse<CoinDto>

    @POST(UPDATE_COINS)
    suspend fun updateCoins(@Body list: List<CoinDto>): ApiResponse<CoinDto>

    @GET(RESET_COINS)
    suspend fun resetCoins(): ApiResponse<CoinDto>

}
