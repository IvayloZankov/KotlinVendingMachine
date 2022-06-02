package com.fosents.kotlinvendingmachine.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
object VendingRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = Gold,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = Gold,
        lightTheme = MaterialTheme.colors.isLight
    )
}