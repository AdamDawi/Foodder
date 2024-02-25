package com.example.foodder.presentation.food_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.domain.use_case.GetMealByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getMealByIdUseCase: GetMealByIdUseCase
): ViewModel(){
    private val _state = mutableStateOf(FoodDetailState())
    val state: State<FoodDetailState> = _state

    fun getMealById(id: Int) {
        getMealByIdUseCase(id).onEach {
            _state.value.meal = it
        }.launchIn(viewModelScope)
    }
    fun changeIsExpanded(b: Boolean){
        _state.value = _state.value.copy(isExpanded = b)
    }
}
