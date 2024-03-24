package com.example.foodder.domain.use_case

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import javax.inject.Inject

class AddMealUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    suspend operator fun invoke(mealEntity: MealEntity) {
        if (mealEntity.strMeal.isNotEmpty() &&
            mealEntity.strMealThumb.isNotEmpty() &&
            mealEntity.strInstructions.isNotEmpty()
        )
            repository.addMeal(mealEntity) //adding when meal is valid
    }
}