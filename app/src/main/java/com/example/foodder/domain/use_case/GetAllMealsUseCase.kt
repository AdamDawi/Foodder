package com.example.foodder.domain.use_case

import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.repository.FoodDbRepository
import com.example.foodder.domain.util.FoodOrder
import com.example.foodder.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAllMealsUseCase @Inject constructor(
    private val repository: FoodDbRepository
) {
    operator fun invoke(
        foodOrder: FoodOrder = FoodOrder.Date(OrderType.Ascending),
        filter: String
    ): Flow<Resource<List<MealEntity>>> = flow{
        try {
            emit(Resource.Loading())
            var meals = emptyList<MealEntity>()
            repository.getAllMeals().first().apply {
                meals = when(foodOrder.orderType){
                    is OrderType.Ascending ->{
                        when(foodOrder){
                            is FoodOrder.Date -> this
                            is FoodOrder.Name -> this.sortedBy { it.strMeal }
                        }
                    }
                    is OrderType.Descending ->{
                        when(foodOrder){
                            is FoodOrder.Date -> this.reversed()
                            is FoodOrder.Name -> this.sortedByDescending { it.strMeal }
                        }
                    }
                }
            }
            if(filter != "Default") meals = meals.filter { it.strCategory == filter }
            emit(Resource.Success(meals))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach database."))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}