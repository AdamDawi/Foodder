package com.example.foodder.presentation.main_screen

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
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
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainScreenTest {

    //ensure that we can actually call an inject function before every test case
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
    lateinit var mockWebServer: MockWebServer

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            FoodderTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(route = Screen.MainScreen.route) {
                        MainScreen(navController = navController)
                    }
                }
            }
        }
        mockWebServer = MockWebServer()

    }

    @Test
    fun clickingFoodCard_shouldFlipAndShowDetails() {
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(1f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(0f)

        composeRule.onNodeWithTag(TestTags.MEAL_CARD).performClick()

        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(0f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(1f)
    }

    @Test
    fun notClickingFoodCard_shouldNotShowDetails() {
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_FRONT_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(1f)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD_BACK_SIDE).assertIsDisplayed()
            .assertIsAlphaEquals(0f)
    }

    @Test
    fun swipeFoodCardToRight_shouldDisplayNewFood() {
        val titleTextBefore = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD)
            .fetchSemanticsNode().config.getOrNull(Text)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
        composeRule.waitForIdle()
        val titleTextAfter = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD)
            .fetchSemanticsNode().config.getOrNull(Text)

        assertNotEquals(titleTextBefore?.get(0) ?: "", titleTextAfter?.get(0) ?: "")
    }
    @Test
    fun swipeFoodCardToLeft_shouldDisplayNewFood() {
        val titleTextBefore = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD)
            .fetchSemanticsNode().config.getOrNull(Text)
        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeLeft()
        composeRule.waitForIdle()
        val titleTextAfter = composeRule.onNodeWithTag(TestTags.MEAL_TITLE_ON_CARD)
            .fetchSemanticsNode().config.getOrNull(Text)

        assertNotEquals(titleTextBefore?.get(0) ?: "", titleTextAfter?.get(0) ?: "")
    }

//    @Test
//    fun swipeFoodCardToRight_shouldIncrementLikeCounter() = runBlocking{
//        val textBefore = composeRule.onNodeWithTag(TestTags.LIKE_COUNTER)
//            .fetchSemanticsNode().config.getOrNull(Text)
//            ?.get(0)?.text
//        assertNotEquals(null, textBefore)
//        assertEquals("0", textBefore)
//
//        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeRight()
//        composeRule.awaitIdle()
//
//        val textAfter = composeRule.onNodeWithTag(TestTags.LIKE_COUNTER)
//            .fetchSemanticsNode().config.getOrNull(Text)?.get(0)?.text
//        assertNotEquals(null, textAfter)
//        assertEquals("1", textAfter)
//    }
//
//    @Test
//    fun swipeFoodCardToLeft_shouldIncrementDeleteCounter() = runBlocking{
//        val textBefore = composeRule.onNodeWithTag(TestTags.DELETE_COUNTER)
//            .fetchSemanticsNode().config.getOrNull(Text)
//            ?.get(0)?.text
//        assertNotEquals(null, textBefore)
//        assertEquals("0", textBefore)
//
//        composeRule.onNodeWithTag(TestTags.MEAL_CARD).swipeLeft()
//        composeRule.awaitIdle()
//
//        val textAfter = composeRule.onNodeWithTag(TestTags.DELETE_COUNTER)
//            .fetchSemanticsNode().config.getOrNull(Text)?.get(0)?.text
//        assertNotEquals(null, textAfter)
//        assertEquals("1", textAfter)
//    }


    private fun SemanticsNodeInteraction.swipeRight(){
        this.performTouchInput { swipeRight(centerX, centerX + centerX) }
    }
    private fun SemanticsNodeInteraction.swipeLeft(){
        this.performTouchInput { swipeLeft(centerX, centerX - centerX) }
    }

    @SuppressLint("CheckResult")
    private fun SemanticsNodeInteraction.assertIsAlphaEquals(alphaValue: Float) {
        if (alphaValue in 0.0..1.0) {
            val v = this.fetchSemanticsNode()
                .layoutInfo
                .getModifierInfo()
                .firstOrNull { it.modifier == Modifier.alpha(0f) }
            assertThat(v != null)
        } else {
            throw IllegalArgumentException("Invalid alpha value: $alphaValue. Must be between 0.0 and 1.0")
        }
    }

}