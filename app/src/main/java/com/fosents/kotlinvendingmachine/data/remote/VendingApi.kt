package com.fosents.kotlinvendingmachine.data.remote

import com.fosents.kotlinvendingmachine.data.remote.dto.CoinDto
import com.fosents.kotlinvendingmachine.data.remote.dto.ProductDto
import com.fosents.kotlinvendingmachine.util.RequestUrl.DECREASE_PRODUCT
import com.fosents.kotlinvendingmachine.util.RequestUrl.GET_COINS
import com.fosents.kotlinvendingmachine.util.RequestUrl.GET_PRODUCTS
import com.fosents.kotlinvendingmachine.util.RequestUrl.RESET_COINS
import com.fosents.kotlinvendingmachine.util.RequestUrl.RESET_PRODUCTS
import com.fosents.kotlinvendingmachine.util.RequestUrl.UPDATE_COINS
import retrofit2.http.*

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
