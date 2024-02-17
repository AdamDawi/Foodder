package com.example.foodder.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodder.domain.model.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FoodDatabase: RoomDatabase(){
    abstract fun mealDao(): MealDao
}