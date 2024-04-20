package com.example.foodder.domain.use_case

import com.example.foodder.common.Resource
import com.example.foodder.data.remote.dto.toCategory
import com.example.foodder.domain.model.Category
import com.example.foodder.domain.repository.FoodApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: FoodApiRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> = flow{
        try {
            emit(Resource.Loading())
            val categories = repository.getCategories().categories.map { it.toCategory() }
            emit(Resource.Success(categories))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}