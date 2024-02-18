package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.use_case.FavouriteFoodScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteFoodViewModel @Inject constructor(
    private val favouriteFoodScreenUseCases: FavouriteFoodScreenUseCases,
): ViewModel() {

    private val _state = mutableStateOf(FavouriteFoodState())
    val state: State<FavouriteFoodState> = _state

    init {
        getAllMeals()
    }
    private fun getAllMeals(){
        favouriteFoodScreenUseCases.getAllMealsUseCase().onEach {
            _state.value = _state.value.copy(meals = it)
        }.launchIn(viewModelScope)
    }
    fun getMealById(id: Int): Flow<MealEntity> {
        return favouriteFoodScreenUseCases.getMealByIdUseCase(id)
    }
    fun deleteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            favouriteFoodScreenUseCases.deleteMealUseCase(mealEntity)
        }
    }
    fun updateMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            favouriteFoodScreenUseCases.updateMealUseCase(mealEntity)
        }
    }

}