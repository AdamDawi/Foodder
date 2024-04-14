package com.example.foodder.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodder.common.TestTags
import com.example.foodder.presentation.main_screen.components.AnimatedCounter
import com.example.foodder.presentation.splash_screen.SplashScreen
import com.example.foodder.presentation.ui.theme.FoodderTheme
import com.example.foodder.presentation.util.Screen
import org.junit.Rule
import org.junit.Test

class AnimationTests {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun animatedCounter_IncrementsValueAndDisplaysCorrectly(){
        rule.mainClock.autoAdvance = false

        var counter by mutableStateOf(0)

        rule.setContent {
            AnimatedCounter(count = counter)
        }

        rule.onNodeWithText("0").assertIsDisplayed()

        counter++
        rule.mainClock.advanceTimeBy(500L)

        rule.onNodeWithText("1").assertIsDisplayed()
    }

    @Test
    fun splashScreen_PlaysAnimation(){
        rule.mainClock.autoAdvance = false

        rule.setContent {
            val navController = rememberNavController()
            FoodderTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(route = Screen.MainScreen.route) {
                        Column(modifier = Modifier.fillMaxSize()) {
                        }
                    }
                    composable(route = Screen.SplashScreen.route) {
                        SplashScreen(navController = navController)
                    }
                }
            }
        }
        rule.mainClock.advanceTimeBy(1000L)
        rule.onNodeWithTag(TestTags.SPLASH_SCREEN_ANIMATION).assertIsDisplayed()
    }
}