package com.example.foodder.domain.use_case

data class FavouriteFoodScreenUseCases (
    val deleteMealUseCase: DeleteMealUseCase,
    val getAllMealsUseCase: GetAllMealsUseCase,
    val getMealByIdUseCase: GetMealByIdUseCase,
    val updateMealUseCase: UpdateMealUseCase
)