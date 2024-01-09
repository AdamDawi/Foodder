package com.example.foodder.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.Meal
import com.example.foodder.domain.use_case.get_random_food.GetRandomFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomFoodUseCase: GetRandomFoodUseCase
): ViewModel() {
    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    init {
        getRandomFood()
    }

    private fun getRandomFood(){
        getRandomFoodUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = MainState(meal = result.data?.get(0) ?: Meal())
                }
                is Resource.Error -> {
                    _state.value = MainState(error = result.message ?: "An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = MainState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeImageOffset(dragAmount: Offset) {
        _state.value = _state.value.copy(
            imageOffset =
            Offset(
                _state.value.imageOffset.x+dragAmount.x,
                _state.value.imageOffset.y+dragAmount.y
            )
        )
    }

    private fun resetImageOffset(){
        _state.value = _state.value.copy(imageOffset = Offset(0f, 0f))
    }

    fun checkSwipeBounds() {
        if(state.value.imageOffset.x>120 && !state.value.isLoading){
            resetImageOffset()
            getRandomFood()
            Log.e("Swipe", "Right")
        }
        else if(state.value.imageOffset.x<-120 && !state.value.isLoading){
            resetImageOffset()
            getRandomFood()
            Log.e("Swipe", "Left")
        }
        else resetImageOffset()
    }
}