package com.example.foodder.data.repository

import com.example.foodder.data.remote.FoodApi
import com.example.foodder.data.remote.dto.FoodDto
import com.example.foodder.domain.repository.FoodApiRepository
import javax.inject.Inject

class FoodApiRepositoryImpl @Inject constructor(
    private val api: FoodApi
): FoodApiRepository {
    override suspend fun getRandomFood(): FoodDto {
        return api.getRandomFood()
    }
}