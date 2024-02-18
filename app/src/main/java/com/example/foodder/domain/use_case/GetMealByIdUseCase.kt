package com.example.foodder.domain.use_case

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealByIdUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    operator fun invoke(id: Int): Flow<MealEntity> {
        return repository.getMealById(id)
    }
}