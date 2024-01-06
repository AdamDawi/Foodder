package com.example.foodder.presentation.main_screen

import com.example.foodder.domain.model.Meal

data class MainState(
    val isLoading: Boolean = false,
    val meal: List<Meal> = emptyList(),
    val error: String = ""
)
