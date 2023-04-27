package com.fosents.kotlinvendingmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import com.fosents.kotlinvendingmachine.navigation.SetupNavGraph
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.theme.KotlinVendingMachineTheme
import com.fosents.kotlinvendingmachine.ui.theme.VendingRippleTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinVendingMachineTheme {
                CompositionLocalProvider(LocalRippleTheme provides VendingRippleTheme) {
                    navHostController = rememberAnimatedNavController()
                    SetupNavGraph(navController = navHostController)
                }
            }
        }
        SoundManager.getInstance().loadSounds(this)
    }
}