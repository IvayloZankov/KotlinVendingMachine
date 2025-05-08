package com.fosents.kotlinvendingmachine.navigation

sealed class Screen(val route: String) {
    data object Products: Screen("products_screen")
    data object Coins: Screen("coins_screen/{productId}") {
        fun passProductId(productId: Int): String {
            return "coins_screen/$productId"
        }
    }
    data object Maintenance: Screen("maintenance_screen")
}
