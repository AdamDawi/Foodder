package com.example.foodder.domain.use_case

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMealsUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    operator fun invoke(): Flow<List<MealEntity>>{
        return repository.getAllMeals()
    }
}