package com.example.foodder.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodder.common.TestTags
import com.example.foodder.di.AppModule
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodScreen
import com.example.foodder.presentation.food_detail_screen.FoodDetailScreen
import com.example.foodder.presentation.main_screen.MainScreen
import com.example.foodder.presentation.ui.theme.FoodderTheme
import com.example.foodder.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FoodEndToEndTest {
    //ensure that we can actually call an inject function before every test case
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            FoodderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Screen.MainScreen.route
                ){
                    composable(route = Screen.MainScreen.route){
                        MainScreen(navController = navController)
                    }
                    composable(route = Screen.FavouriteFoodScreen.route){
                        FavouriteFoodScreen(navController = navController)
                    }
                    composable(route = Screen.FoodDetailScreen.route + "/{id}",
                        arguments = listOf(
                            navArgument("id"){
                                type = NavType.IntType
                                defaultValue = 0
                                nullable = false
                            }
                        )
                    ){
                        val idd = if(it.arguments!=null) it.arguments!!.getInt("id") else 0
                        FoodDetailScreen(id = idd, navController = navController)
                    }
                }
            }
        }
    }
    //Tests don't work when you run them all at once
    //TODO make them work together
    @Test
    fun swipeCardToRight_deleteFromFavListBySwipeAfterwards(){
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
        composeRule.onNodeWithContentDescription("List of favourite food").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("food").swipeLeft()
        composeRule.onNodeWithContentDescription("food").assertDoesNotExist()
    }

    @Test
    fun swipeCardToRight_deleteFromFavListByClickAfterwards(){
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
        composeRule.onNodeWithContentDescription("List of favourite food").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Delete food").performClick()
        composeRule.onNodeWithContentDescription("food").assertDoesNotExist()
    }

    @Test
    fun swipeCardToRight_deleteFromFavListByClickAfterwards_andUndoDelete_shouldNotDisplayFood(){
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
        composeRule.onNodeWithContentDescription("List of favourite food").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Delete food").performClick()
        composeRule.onNodeWithContentDescription("food").assertDoesNotExist()
        composeRule.onNodeWithText("Undo").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsNotDisplayed()
    }

    @Test
    fun swipeCardToRight_deleteFromFavListByClickAfterwards_andUndoDelete_refreshFoodList_shouldDisplayFood(){
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
        composeRule.onNodeWithContentDescription("List of favourite food").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Delete food").performClick()
        composeRule.onNodeWithContentDescription("food").assertDoesNotExist()
        composeRule.onNodeWithText("Undo").performClick()
        composeRule.onNodeWithContentDescription("food").assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.PULL_TO_REFRESH).swipeDown()
        composeRule.onNodeWithContentDescription("food").assertIsDisplayed()
    }

    private fun SemanticsNodeInteraction.swipeDown(){
        this.performTouchInput { swipeDown(centerY, centerY + centerY, durationMillis= 500) }
    }
    private fun SemanticsNodeInteraction.swipeRight(){
        this.performTouchInput { swipeRight(centerX, centerX + centerX, durationMillis= 500) }
    }
    private fun SemanticsNodeInteraction.swipeLeft(){
        this.performTouchInput { swipeLeft(centerX, -centerX - centerX, durationMillis= 500) }
    }
}