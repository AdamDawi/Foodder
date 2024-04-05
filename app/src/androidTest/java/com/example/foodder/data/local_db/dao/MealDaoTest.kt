package com.example.foodder.data.local_db.dao

import androidx.test.filters.SmallTest
import com.example.foodder.data.local_db.FoodDatabase
import com.example.foodder.di.AppModule
import com.example.foodder.domain.model.MealEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
@SmallTest
class MealDaoTest{

    @Inject
    lateinit var database: FoodDatabase
    private lateinit var dao: MealDao
    private lateinit var meal: MealEntity

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun setUp(){
        hiltRule.inject()
        dao = database.mealDao
        meal = MealEntity(
            id = 1,
            strArea = "French",
            strMeal = "Chocolate Souffle",
            strCategory = "Dessert",
            strInstructions = "Heat oven to 220C/fan 200C/gas 7 and place a baking tray on the top shelf. For the sauce, heat the cream and sugar until boiling. Remove from the heat, stir in the chocolate and butter until melted, then keep warm.\r\nBrush 6 x 150ml ramekins with melted butter, sprinkle with the 2 tbsp caster sugar, then tip out any excess. Melt the chocolate and cream in a bowl over a pan of simmering water, cool, then mix in the egg yolks. Whisk the egg whites until they hold their shape, then add the sugar, 1 tbsp at a time, whisking back to the same consistency. Mix a spoonful into the chocolate, then gently fold in the rest.\r\nWorking quickly, fill the ramekins, wipe the rims clean and run your thumb around the edges. Turn oven down to 200C/fan 180C/gas 6, place the ramekins onto the baking tray, then bake for 8-10 mins until risen with a slight wobble. Don\u2019t open the oven door too early as this may make them collapse.\r\nOnce the souffl\u00e9s are ready, dust with icing sugar, scoop a small hole from their tops, then pour in some of the hot chocolate sauce. Replace the lids and serve straight away.",
            strMealThumb = "https://www.themealdb.com/images/media/meals/twspvx1511784937.jpg",
            strIngredients = mutableListOf("sugar"),
            strMeasurements = mutableListOf("1 spoon")
        )
    }
    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertMeal() = runBlocking{
        dao.addMeal(meal)
        val result = dao.getAllMeals().first()
        assertThat(result).contains(meal)
    }

    @Test
    fun deleteMeal() = runBlocking {
        dao.addMeal(meal)
        dao.deleteMeal(meal)
        val result = dao.getAllMeals().first()
        assertThat(result).doesNotContain(meal)
    }

    @Test
    fun getMealById() = runBlocking {
        dao.addMeal(meal)
        val result = dao.getMealById(1).first()
        assertThat(result).isEqualTo(meal)
    }

    @Test
    fun updateMeal() = runBlocking {
        dao.addMeal(meal)
        val newMeal = meal.copy(strMeal = "Egg")
        dao.updateMeal(newMeal)
        val result = dao.getMealById(1).first()
        assertThat(result).isEqualTo(newMeal)
    }

}