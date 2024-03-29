package com.example.foodder.domain.use_case

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import javax.inject.Inject

class DeleteMealUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    suspend operator fun invoke(mealEntity: MealEntity){
        repository.deleteMeal(mealEntity)
    }
}