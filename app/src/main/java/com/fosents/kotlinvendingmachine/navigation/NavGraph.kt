package com.fosents.kotlinvendingmachine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fosents.kotlinvendingmachine.ui.screen.CoinsScreen
import com.fosents.kotlinvendingmachine.ui.screen.MaintenanceScreen
import com.fosents.kotlinvendingmachine.ui.screen.ProductsScreen
import com.fosents.kotlinvendingmachine.util.Constants.ARG_PRODUCT_ID

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Products.route
    ) {
        composable(route = Screen.Products.route) {
            ProductsScreen(navController = navController)
        }
        composable(route = Screen.Coins.route,
        arguments = listOf(navArgument(ARG_PRODUCT_ID) {
            type = NavType.IntType
        }))
        {
            CoinsScreen(navHostController = navController)
        }
        composable(route = Screen.Maintenance.route) {
            MaintenanceScreen(navController = navController)
        }
    }
}
