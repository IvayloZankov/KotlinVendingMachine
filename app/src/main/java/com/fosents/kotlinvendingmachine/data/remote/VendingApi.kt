package com.fosents.kotlinvendingmachine.data.remote

import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import com.fosents.kotlinvendingmachine.util.RequestUrl.DECREASE_PRODUCT
import com.fosents.kotlinvendingmachine.util.RequestUrl.GET_COINS
import com.fosents.kotlinvendingmachine.util.RequestUrl.GET_PRODUCTS
import com.fosents.kotlinvendingmachine.util.RequestUrl.RESET_COINS
import com.fosents.kotlinvendingmachine.util.RequestUrl.RESET_PRODUCTS
import com.fosents.kotlinvendingmachine.util.RequestUrl.UPDATE_COINS
import retrofit2.http.*

interface VendingApi {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): ApiResponse<Product>

    @FormUrlEncoded
    @POST(DECREASE_PRODUCT)
    suspend fun decreaseProduct(
        @FieldMap params: Map<String, Int>
    ): ApiResponse<Product>

    @GET(RESET_PRODUCTS)
    suspend fun resetProducts(): ApiResponse<Product>

    @GET(GET_COINS)
    suspend fun getCoins(): ApiResponse<Coin>

    @POST(UPDATE_COINS)
    suspend fun updateCoins(@Body list: List<Coin>): ApiResponse<Coin>

    @GET(RESET_COINS)
    suspend fun resetCoins(): ApiResponse<Coin>

}