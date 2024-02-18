package com.example.foodder.presentation.util

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object FavouriteFoodScreen: Screen("favourite_food_screen")
    object FoodDetailScreen: Screen("food_detail_screen")
}