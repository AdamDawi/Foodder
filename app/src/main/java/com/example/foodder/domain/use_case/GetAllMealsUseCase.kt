package com.example.foodder.domain.use_case

import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAllMealsUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    operator fun invoke(): Flow<Resource<List<MealEntity>>> = flow{
        try {
            emit(Resource.Loading())
            repository.getAllMeals().collect{meals ->
                emit(Resource.Success(meals))
            }

        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach database."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}