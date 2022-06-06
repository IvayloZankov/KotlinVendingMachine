package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun AnimatedAlert(content: @Composable (anims: List<Float>) -> Unit) {
    var startAnimation by rememberSaveable { mutableStateOf(false) }
    val alphaAnimSurface by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val alphaAnimTitle by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 100
        )
    )
    val alphaAnimCoins by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 200
        )
    )
    val alphaAnimCoinsButton by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            delayMillis = 300
        )
    )

    val anims = listOf(alphaAnimSurface, alphaAnimTitle, alphaAnimCoins, alphaAnimCoinsButton)

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    content(anims)
}