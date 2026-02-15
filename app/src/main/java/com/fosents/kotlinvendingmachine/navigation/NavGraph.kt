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
import com.fosents.kotlinvendingmachine.ui.screen.CoinsFragment
import com.fosents.kotlinvendingmachine.ui.screen.MaintenanceFragment
import com.fosents.kotlinvendingmachine.ui.screen.ProductsFragment
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
            ProductsFragment(
                onGoMaintenanceClick = {
                    navController.navigate(Screen.Maintenance.route)
                },
                onProductClick = {
                    navController.navigate(Screen.Coins.passProductId(it))
                }
            )
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
            CoinsFragment(
                onAlertOkClick = {
                    navController.popBackStack()
                }
            )
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
            MaintenanceFragment(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
