package com.example.foodder.presentation.food_detail_screen

import androidx.lifecycle.ViewModel
import com.example.foodder.domain.use_case.GetMealByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getMealByIdUseCase: GetMealByIdUseCase
): ViewModel(){

}
