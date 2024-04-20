package com.example.foodder.presentation.favourite_food_screen

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.util.FoodOrder

sealed class FavouriteFoodEvent {
    data class Order(val foodOrder: FoodOrder): FavouriteFoodEvent()
    data class DeleteMeal(val mealEntity: MealEntity): FavouriteFoodEvent()
    data object RestoreMeal: FavouriteFoodEvent()
    data object GetAllMeals : FavouriteFoodEvent()
    data class Filter(val category: String): FavouriteFoodEvent()
}