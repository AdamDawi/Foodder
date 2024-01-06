package com.example.foodder.data.remote.dto

import com.example.foodder.domain.model.Meal

data class FoodDto(
    val meals:List<MealDto>
)

fun FoodDto.toFood(): Meal {
    var meal: Meal? = null

    this.meals.forEach{
        meal = Meal(
            strCategory = it.strCategory,
            strInstructions = it.strInstructions,
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb
        )
    }
    return meal?: Meal()
}