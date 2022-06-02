package com.fosents.kotlinvendingmachine.navigation

sealed class Screen(val route: String) {
    object Products: Screen("products_screen")
    object Coins: Screen("coins_screen/{productId}") {
        fun passProductId(productId: Int): String {
            return "coins_screen/$productId"
        }
    }
    object Maintenance: Screen("maintenance_screen")
}
