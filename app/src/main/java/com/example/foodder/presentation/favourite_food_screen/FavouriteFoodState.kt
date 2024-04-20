package com.example.foodder.presentation.favourite_food_screen

import com.example.foodder.domain.model.MealEntity

data class FavouriteFoodState (
    val meals: List<MealEntity> = emptyList(),
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)