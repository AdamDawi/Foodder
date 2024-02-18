package com.example.foodder.data.local_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foodder.domain.model.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addMeal(mealEntity: MealEntity)

    @Query("select * from `food_table`")
    abstract fun getAllMeals(): Flow<List<MealEntity>>

    @Update
    abstract suspend fun updateMeal(mealEntity: MealEntity)
    @Delete
    abstract suspend fun deleteMeal(mealEntity: MealEntity)

    @Query("select * from `food_table` where id = :id")
    abstract fun getMealById(id: Int): Flow<MealEntity>
}