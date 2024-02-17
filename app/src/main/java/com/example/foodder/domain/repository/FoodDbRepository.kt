package com.example.foodder.domain.repository


import com.example.foodder.domain.model.MealEntity
import kotlinx.coroutines.flow.Flow

interface FoodDbRepository {
    suspend fun addMeal(mealEntity: MealEntity)
    fun getAllMeals(): Flow<List<MealEntity>>
    fun getMealById(id: Int): Flow<MealEntity>
    suspend fun updateMeal(mealEntity: MealEntity)
    suspend fun deleteMeal(mealEntity: MealEntity)
}