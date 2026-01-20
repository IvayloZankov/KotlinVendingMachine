package com.fosents.kotlinvendingmachine.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Products.route
    ) {
        composable(
            route = Screen.Products.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeIn(animationSpec = tween(300))
            }
        ) {
            ProductsScreen(navController = navController)
        }
        composable(
            route = Screen.Coins.route,
            arguments = listOf(navArgument(ARG_PRODUCT_ID) {
                type = NavType.IntType
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeOut(animationSpec = tween(300))
            }
        ) {
            CoinsScreen(navHostController = navController)
        }
        composable(
            route = Screen.Maintenance.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) +
                        fadeOut(animationSpec = tween(300))
            }
        ) {
            MaintenanceScreen(navController = navController)
        }
    }
}
