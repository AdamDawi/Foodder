package com.example.foodder.domain.use_case

import com.example.foodder.common.Constants
import com.example.foodder.common.Resource
import com.example.foodder.data.repository.FakeDbRepository
import com.example.foodder.domain.model.MealEntity
import com.example.foodder.domain.model.toMealEntity
import com.example.foodder.domain.util.FoodOrder
import com.example.foodder.domain.util.OrderType
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllMealsUseCaseTest{
    private lateinit var getAllMealsUseCase: GetAllMealsUseCase
    private lateinit var fakeDbRepository: FakeDbRepository
    private lateinit var list: List<MealEntity>

    private val category = "Beef"

    @Before
    fun setUp(){

        list = listOf(
            Constants.DUMMY_DATA.toMealEntity(),
            MealEntity(
            id = 1,
            strMeal = "apple",
            strMealThumb = "ddd",
            strInstructions = "hhh",
            strCategory = "Vegetarian"
        ),
            MealEntity(
                id = 2,
                strMeal = "kkkk",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Beef"
            ),
            MealEntity(
                id = 2,
                strMeal = "zzzz",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Beef"
            ),
            MealEntity(
                id = 3,
                strMeal = "ggggg",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Beef"
            ),
            MealEntity(
                id = 4,
                strMeal = "LLLL",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Beef"
            ),
            MealEntity(
                id = 5,
                strMeal = "mmmmp",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Chicken"
            ),
            MealEntity(
                id = 6,
                strMeal = "PP",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Beef"
            ),
            MealEntity(
                id = 7,
                strMeal = "dddds",
                strMealThumb = "ddd",
                strInstructions = "hhh",
                strCategory = "Chicken"
            )
        )
        fakeDbRepository = FakeDbRepository()
        getAllMealsUseCase = GetAllMealsUseCase(fakeDbRepository)
    }
    @Test
    fun `Should get data filtered by Default and Sort by Date Ascending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}

            emittedValues = getAllMealsUseCase(filter = "Default").toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list)
    }

    @Test
    fun `Should get data filtered by Default and Sort by Date Descending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}
            emittedValues = getAllMealsUseCase(filter = "Default", foodOrder = FoodOrder.Date(OrderType.Descending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.reversed())
    }

    @Test
    fun `Should get data filtered by Default and Sort by Name Ascending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}
            emittedValues = getAllMealsUseCase(filter = "Default", foodOrder = FoodOrder.Name(OrderType.Ascending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.sortedBy { it.strMeal })
    }

    @Test
    fun `Should get data filtered by Default and Sort by Name Descending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}
            emittedValues = getAllMealsUseCase(filter = "Default", foodOrder = FoodOrder.Name(OrderType.Descending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.sortedByDescending { it.strMeal })
    }

    //filter by category
    @Test
    fun `Should get data filtered by Category and Sort by Date Ascending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}

            emittedValues = getAllMealsUseCase(filter = category).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.filter { it.strCategory == category })
    }

    @Test
    fun `Should get data filtered by Category and Sort by Date Descending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}
            emittedValues = getAllMealsUseCase(filter = category, foodOrder = FoodOrder.Date(OrderType.Descending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.reversed().filter { it.strCategory == category })
    }
    @Test
    fun `Should get data filtered by Category and Sort by Name Ascending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}

            emittedValues = getAllMealsUseCase(filter = category, foodOrder = FoodOrder.Name(OrderType.Ascending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.sortedBy { it.strMeal }.filter { it.strCategory == category })
    }

    @Test
    fun `Should get data filtered by Category and Sort by Name Descending`(){
        val emittedValues: List<Resource<List<MealEntity>>>
        runBlocking {
            list.forEach{fakeDbRepository.addMeal(it)}

            emittedValues = getAllMealsUseCase(filter = category, foodOrder = FoodOrder.Name(OrderType.Descending)).toList()
        }
        Truth.assertThat(emittedValues[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(emittedValues[1].data).isEqualTo(list.sortedByDescending { it.strMeal }.filter { it.strCategory == category })
    }
}