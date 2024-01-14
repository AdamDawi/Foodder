package com.example.foodder.presentation.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: PaddingValues = PaddingValues(3.dp),
    val large: PaddingValues = PaddingValues(30.dp)
)

val LocalSpacing = compositionLocalOf { Spacing() }
