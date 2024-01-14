package com.example.foodder.presentation.main_screen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.foodder.domain.model.Meal

data class MainState(
    val isLoading: Boolean = false,
    val meal: Meal = Meal(),
    val errorMessage: String = "",
    val cardOffset: Offset = Offset(0f, 0f),
    val cardBorderColor: Color = Color.Transparent,
    val isCardFlipped: Boolean = false,
    val isSwipeToRightShaking: Boolean = false,
    val isSwipeToLeftShaking: Boolean = false
)
