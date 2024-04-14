package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.use_case.FavouriteFoodScreenUseCases
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
    val state: State<FavouriteFoodState> = _state

    private var lastDeletedMeal: MealEntity = MealEntity()

    init {
        getAllMeals()
    }
    private fun getAllMeals(){
        favouriteFoodScreenUseCases.getAllMealsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(meals = result.data ?: emptyList())
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