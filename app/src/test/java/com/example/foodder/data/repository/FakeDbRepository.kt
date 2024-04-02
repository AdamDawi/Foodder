package com.example.foodder.data.repository

import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDbRepository: FoodDbRepository {
    private val db: MutableList<MealEntity> = mutableListOf()
    override suspend fun addMeal(mealEntity: MealEntity) {
        db.add(mealEntity)
    }

    override fun getAllMeals(): Flow<List<MealEntity>> {
        return flow { emit(db) }
    }

    override fun getMealById(id: Int): Flow<MealEntity> {
        for(i in db){
            if(i.id==id) return flow { emit(i) }
        }
        return flow { MealEntity() }
    }

    override suspend fun updateMeal(mealEntity: MealEntity) {
        db[mealEntity.id]=mealEntity
    }

    override suspend fun deleteMeal(mealEntity: MealEntity) {
        db.removeAt(mealEntity.id)
    }


}