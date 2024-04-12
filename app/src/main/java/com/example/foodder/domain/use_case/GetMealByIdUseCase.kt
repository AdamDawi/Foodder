package com.example.foodder.domain.use_case

import android.util.Log
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMealByIdUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    operator fun invoke(id: Int): Flow<Resource<MealEntity>> = flow{
        try {
            emit(Resource.Loading())
            Log.e("Loading", "Loading")
            val meal = repository.getMealById(id).first()
            emit(Resource.Success(meal))
            Log.e("Success", "Success")
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach database."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}