package com.example.foodder.presentation.favourite_food_screen

import com.example.foodder.domain.model.Category
import com.example.foodder.domain.model.MealEntity

data class FavouriteFoodState (
    val meals: List<MealEntity> = emptyList(),
    val errorMessageDb: String = "",
    val isLoadingDb: Boolean = false,
    val errorMessageApi: String = "",
    val isLoadingApi: Boolean = false,
    val isRefreshingDb: Boolean = false,
    val categories: List<Category> = emptyList()
)