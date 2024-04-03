package com.example.foodder.domain.use_case

import com.example.foodder.data.repository.FakeDbRepository
import com.example.foodder.domain.model.MealEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddMealUseCaseTest{
    private lateinit var addMealUseCase: AddMealUseCase
    private lateinit var fakeDbRepository: FakeDbRepository

    @Before
    fun setUp(){
        fakeDbRepository = FakeDbRepository()
        addMealUseCase = AddMealUseCase(fakeDbRepository)
    }
    @Test
    fun `Should not add empty meal to database`(){
        val meal = MealEntity()

        val result: List<MealEntity>
        runBlocking {
            addMealUseCase(meal)
            result = fakeDbRepository.getAllMeals().first()
        }
        assertThat(result).doesNotContain(meal)


    }
    @Test
    fun `Should not add meal with empty strMeal to database`(){
        val meal = MealEntity(
            strArea = "k",
            strCategory = "k",
            strInstructions = "k",
            strMeal = "",
            strMealThumb = "k",
            strIngredients = mutableListOf(),
            strMeasurements = mutableListOf()
        )
        val result: List<MealEntity>
        runBlocking {
            addMealUseCase(meal)
            result = fakeDbRepository.getAllMeals().first()
        }
        assertThat(result).doesNotContain(meal)

    }
    @Test
    fun `Should not add meal with empty strMealThumb to database`(){
        val meal = MealEntity(
            strArea = "k",
            strCategory = "k",
            strInstructions = "k",
            strMeal = "k",
            strMealThumb = "",
            strIngredients = mutableListOf(),
            strMeasurements = mutableListOf()
        )
        val result: List<MealEntity>
        runBlocking {
            addMealUseCase(meal)
            result = fakeDbRepository.getAllMeals().first()
        }
        assertThat(result).doesNotContain(meal)

    }
    @Test
    fun `Should not add meal with empty instructions to database`(){
        val meal = MealEntity(
            strArea = "k",
            strCategory = "k",
            strInstructions = "",
            strMeal = "k",
            strMealThumb = "k",
            strIngredients = mutableListOf(),
            strMeasurements = mutableListOf()
        )
        val result: List<MealEntity>
        runBlocking {
            addMealUseCase(meal)
            result = fakeDbRepository.getAllMeals().first()
        }
        assertThat(result).doesNotContain(meal)

    }
    @Test
    fun `Should add complete meal data to database`(){
        val meal = MealEntity(
            strArea = "k",
            strCategory = "k",
            strInstructions = "k",
            strMeal = "k",
            strMealThumb = "k",
            strIngredients = mutableListOf(),
            strMeasurements = mutableListOf()
        )
        val result: List<MealEntity>
        runBlocking {
            addMealUseCase(meal)
            result = fakeDbRepository.getAllMeals().first()
        }
        assertThat(result).contains(meal)
    }
}