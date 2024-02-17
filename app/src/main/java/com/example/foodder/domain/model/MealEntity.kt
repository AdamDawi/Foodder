package com.example.foodder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val strArea: String = "",
    val strCategory: String = "",
    val strInstructions: String = "",
    val strMeal: String = "",
    val strMealThumb: String = "",
    val strIngredients: MutableList<String> = mutableListOf()
)
