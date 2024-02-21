package com.example.foodder.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val small: Dp = 3.dp,
    val large: Dp = 30.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }
