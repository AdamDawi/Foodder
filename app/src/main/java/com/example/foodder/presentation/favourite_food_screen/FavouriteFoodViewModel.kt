package com.example.foodder.presentation.favourite_food_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.use_case.FavouriteFoodScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteFoodViewModel @Inject constructor(
    private val favouriteFoodScreenUseCases: FavouriteFoodScreenUseCases
): ViewModel() {

    private val _state = MutableStateFlow(FavouriteFoodState())
    val state: MutableStateFlow<FavouriteFoodState> = _state

    private var lastDeletedMeal: MealEntity = MealEntity()

    init {
        getAllMeals()
    }
    fun getAllMeals(){
        favouriteFoodScreenUseCases.getAllMealsUseCase().onEach { result ->
            Log.e("result", result.data.toString())
            Log.e("result msg", result.message.toString())
            when(result){
                is Resource.Success -> {
                    _state.update { it.copy(meals = result.data ?: emptyList(), isLoading = false) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(errorMessage = result.message ?: "An unexpected error") }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)

    }
    fun deleteMeal(mealEntity: MealEntity){
        lastDeletedMeal = mealEntity
        viewModelScope.launch(Dispatchers.IO) {
            favouriteFoodScreenUseCases.deleteMealUseCase(mealEntity)
        }
    }

    fun undoDeleteMeal() {
        viewModelScope.launch {
            favouriteFoodScreenUseCases.addMealUseCase(lastDeletedMeal)
        }
        lastDeletedMeal = MealEntity()
    }
}