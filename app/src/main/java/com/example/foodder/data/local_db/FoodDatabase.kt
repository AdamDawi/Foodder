package com.example.foodder.data.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodder.data.local_db.dao.MealDao
import com.example.foodder.domain.model.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class FoodDatabase: RoomDatabase(){
    abstract val mealDao: MealDao
}