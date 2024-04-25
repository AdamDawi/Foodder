package com.example.foodder.data.repository

import com.example.foodder.data.local_db.dao.MealDao
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow

class FoodDbRepositoryImpl(
    private val mealDao: MealDao
): FoodDbRepository{
    override suspend fun addMeal(mealEntity: MealEntity){
        mealDao.addMeal(mealEntity)
    }
    override fun getAllMeals(): Flow<List<MealEntity>>{
        return mealDao.getAllMeals()
    }
    override fun getMealById(id: Int): Flow<MealEntity> = mealDao.getMealById(id)
    override suspend fun updateMeal(mealEntity: MealEntity){
        mealDao.updateMeal(mealEntity)
    }
    override suspend fun deleteMeal(mealEntity: MealEntity){
        mealDao.deleteMeal(mealEntity)
    }
}