package com.example.foodder.domain.use_case

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import javax.inject.Inject

class AddMealUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    suspend operator fun invoke(meal: MealEntity) {
        if (meal.strMeal.isNotEmpty() &&
            meal.strMealThumb.isNotEmpty() &&
            meal.strInstructions.isNotEmpty()
        ){
            repository.addMeal(meal) //adding when meal is valid
        }

    }
}