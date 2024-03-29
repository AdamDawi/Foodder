package com.example.foodder.domain.use_case

import com.example.foodder.common.Resource
import com.example.foodder.data.remote.dto.toMeal
import com.example.foodder.domain.model.Meal
import com.example.foodder.domain.repository.FoodApiRepository
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRandomFoodUseCase @Inject constructor(
    private val repository: FoodApiRepository
) {
    operator fun invoke(): Flow<Resource<List<Meal>>> = flow{
        try {
            emit(Resource.Loading())
            val meal = repository.getRandomFood().meals.map { it.toMeal() }
            emit(Resource.Success(meal))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}