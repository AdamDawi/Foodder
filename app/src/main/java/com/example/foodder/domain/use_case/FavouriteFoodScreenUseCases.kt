package com.example.foodder.domain.use_case

data class FavouriteFoodScreenUseCases (
    val deleteMealUseCase: DeleteMealUseCase,
    val getAllMealsUseCase: GetAllMealsUseCase,
    val addMealUseCase: AddMealUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase
)