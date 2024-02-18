package com.example.foodder.domain.model

data class Meal(
    val strArea: String = "",
    val strCategory: String = "",
    val strInstructions: String = "",
    val strMeal: String = "",
    val strMealThumb: String = "",
    val strIngredients: MutableList<String> = mutableListOf()
)

fun Meal.toMealEntity(): MealEntity {
    return MealEntity(
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

