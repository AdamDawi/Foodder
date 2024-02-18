package com.example.foodder.domain.repository

import com.example.foodder.data.remote.dto.FoodDto

interface FoodApiRepository {
    suspend fun getRandomFood(): FoodDto
}