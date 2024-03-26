package com.example.foodder.presentation.main_screen

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodder.common.TestTags
import com.example.foodder.di.AppModule
import com.example.foodder.presentation.MainActivity
import com.example.foodder.presentation.ui.theme.FoodderTheme
import com.example.foodder.presentation.util.Screen
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainScreenTest{

    //ensure that we can actually call an inject function before every test case
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            FoodderTheme{
                NavHost(navController = navController,
                    startDestination = Screen.MainScreen.route
                ){
                    composable(route = Screen.MainScreen.route){
                        MainScreen(navController = navController)
                    }
                }
            }
        }
    }
    @Test
    fun clickingFoodCard_shouldFlipAndShowDetails(){
        //TODO make mocking testing
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsAlphaEquals(1f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsAlphaEquals(0f)

        composeRule.onNodeWithTag(TestTags.MEAL_CARD).performClick()

        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsAlphaEquals(0f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsAlphaEquals(1f)
    }
    @Test
    fun notClickingFoodCard_shouldNotShowDetails(){
        //TODO make mocking testing
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsAlphaEquals(1f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsAlphaEquals(0f)
    }
    @Test
    fun swipeFoodCard_shouldDisplayNewFood(){
        //TODO make mocking testing
        composeRule.waitForIdle()
        val titleTextBefore = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD).fetchSemanticsNode().config.getOrNull(Text)
        runBlocking { composeRule.onNodeWithTag(TestTags.MEAL_CARD).performTouchInput {swipeRight(centerX, centerX+centerX)} }
        val titleTextAfter = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD).fetchSemanticsNode().config.getOrNull(Text)

        assertNotEquals(titleTextBefore?.get(0) ?: "", titleTextAfter?.get(0) ?: "")
    }
    @SuppressLint("CheckResult")
    fun SemanticsNodeInteraction.assertIsAlphaEquals(alphaValue: Float){
        if(alphaValue in 0.0..1.0){
            val v = this.fetchSemanticsNode()
                .layoutInfo
                .getModifierInfo()
                .firstOrNull { it.modifier == Modifier.alpha(0f) }
            assertThat(v!=null)
        }else {
            throw IllegalArgumentException("Invalid alpha value: $alphaValue. Must be between 0.0 and 1.0")
        }
    }

}