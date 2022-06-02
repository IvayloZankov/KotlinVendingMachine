package com.fosents.kotlinvendingmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fosents.kotlinvendingmachine.navigation.SetupNavGraph
import com.fosents.kotlinvendingmachine.ui.theme.KotlinVendingMachineTheme
import com.fosents.kotlinvendingmachine.ui.theme.VendingRippleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinVendingMachineTheme {
                CompositionLocalProvider(LocalRippleTheme provides VendingRippleTheme) {
                    navHostController = rememberNavController()
                    SetupNavGraph(navController = navHostController)
                }
            }
        }
    }
}