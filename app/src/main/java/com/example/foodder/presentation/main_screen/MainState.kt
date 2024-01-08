package com.example.foodder.presentation.main_screen

import androidx.compose.ui.geometry.Offset
import com.example.foodder.domain.model.Meal

data class MainState(
    val isLoading: Boolean = false,
    val meal: Meal = Meal(),
    val error: String = "",
    val imageOffset: Offset = Offset(0f, 0f)
)
