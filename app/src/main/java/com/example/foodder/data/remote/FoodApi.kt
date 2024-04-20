package com.example.foodder.data.remote

import com.example.foodder.data.remote.dto.CategoriesDto
import com.example.foodder.data.remote.dto.FoodDto
import retrofit2.http.GET

interface FoodApi {
    @GET("v1/1/random.php")
    suspend fun getRandomFood(): FoodDto
    @GET("v1/1/categories.php")
    suspend fun getCategories(): CategoriesDto
}