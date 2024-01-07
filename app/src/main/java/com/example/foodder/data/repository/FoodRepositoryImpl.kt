package com.example.foodder.data.repository

import com.example.foodder.data.remote.FoodApi
import com.example.foodder.data.remote.dto.FoodDto
import com.example.foodder.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val api: FoodApi
): FoodRepository {
    override suspend fun getRandomFood(): FoodDto {
        return api.getRandomFood()
    }
}