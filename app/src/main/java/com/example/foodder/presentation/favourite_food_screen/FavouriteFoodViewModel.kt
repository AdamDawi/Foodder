package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.use_case.FavouriteFoodScreenUseCases
import com.example.foodder.domain.util.FoodOrder
import com.example.foodder.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteFoodViewModel @Inject constructor(
    private val favouriteFoodScreenUseCases: FavouriteFoodScreenUseCases
): ViewModel() {

    private val _state = mutableStateOf(FavouriteFoodState())
    val state: MutableState<FavouriteFoodState> = _state

    private val _foodOrder = mutableStateOf<FoodOrder>(FoodOrder.Date(OrderType.Ascending))
    val foodOrder: MutableState<FoodOrder> = _foodOrder

    private var lastDeletedMeal: MealEntity = MealEntity()
    private var isDataChanged: Boolean = true

    init {
        getAllMeals(FoodOrder.Date(OrderType.Ascending))
    }
    fun onEvent(event: FavouriteFoodEvent){
        when(event){
            is FavouriteFoodEvent.GetAllMeals ->{
                //when data is the same is not worth for loading to UI
                if(isDataChanged) {
                    isDataChanged = false
                    _state.value = _state.value.copy(isRefreshing = true)
                    getAllMeals(event.foodOrder)
                }
            }
            is FavouriteFoodEvent.Order ->{
                if(_foodOrder.value::class == event.foodOrder::class &&
                    foodOrder.value.orderType == event.foodOrder.orderType){
                    return
                }
                _foodOrder.value = event.foodOrder
                getAllMeals(event.foodOrder)
            }
            is FavouriteFoodEvent.DeleteMeal ->{
                deleteMeal(event.mealEntity)
            }
            is FavouriteFoodEvent.RestoreMeal ->{
                undoDeleteMeal()
            }
        }
    }

    private fun getAllMeals(foodOrder: FoodOrder) {
            favouriteFoodScreenUseCases.getAllMealsUseCase(foodOrder).onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(meals = result.data?: emptyList(), isLoading = false, isRefreshing = false)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(errorMessage = result.message ?: "An unexpected error")
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

    }
    private fun deleteMeal(mealEntity: MealEntity){
        lastDeletedMeal = mealEntity
        isDataChanged=true
        viewModelScope.launch(Dispatchers.IO) {
            favouriteFoodScreenUseCases.deleteMealUseCase(mealEntity)
        }
        //better than loading all meals from db
        //delete directly from state
        _state.value = _state.value.copy(meals = _state.value.meals.filterNot { it.id == mealEntity.id })
    }

    private fun undoDeleteMeal() {
        viewModelScope.launch {
            favouriteFoodScreenUseCases.addMealUseCase(lastDeletedMeal)
        }
        lastDeletedMeal = MealEntity()
    }
}