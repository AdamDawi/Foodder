package com.example.foodder.presentation.food_detail_screen

import com.example.foodder.domain.model.MealEntity

data class FoodDetailState (
    var meal: MealEntity = MealEntity(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)