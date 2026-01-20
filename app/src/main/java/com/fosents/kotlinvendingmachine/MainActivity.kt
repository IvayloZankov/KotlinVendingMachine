package com.fosents.kotlinvendingmachine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fosents.kotlinvendingmachine.navigation.SetupNavGraph
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.theme.Gold
import com.fosents.kotlinvendingmachine.ui.theme.KotlinVendingMachineTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    private val myRippleConfiguration = RippleConfiguration(
        color = Gold, rippleAlpha = RippleAlpha(
            draggedAlpha = 1f,
            focusedAlpha = 1f,
            hoveredAlpha = 1f,
            pressedAlpha = 0.2f
        )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinVendingMachineTheme {
                CompositionLocalProvider(LocalRippleConfiguration provides myRippleConfiguration) {
                    navHostController = rememberNavController()
                    SetupNavGraph(navController = navHostController)
                }
            }
        }
        SoundManager.getInstance().loadSounds(this)
    }
}
