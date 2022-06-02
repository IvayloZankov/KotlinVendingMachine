package com.fosents.kotlinvendingmachine.util

object RequestUrl {
    const val BASE_URL = "https://zankov.dev/vending/"
    private const val PATH = "/vending/"
    const val GET_PRODUCTS = PATH + "getProducts"
    const val DECREASE_PRODUCT = PATH + "decreaseProduct"
    const val RESET_PRODUCTS = PATH + "resetProducts"
    const val GET_COINS = PATH + "getCoins"
    const val RESET_COINS = PATH + "resetCoins"
    const val UPDATE_COINS = PATH + "updateCoins"
}