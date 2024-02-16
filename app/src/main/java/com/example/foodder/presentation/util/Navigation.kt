package com.example.foodder.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodScreen
import com.example.foodder.presentation.main_screen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.MainScreen.route
        ){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route = Screen.FavouriteFoodScreen.route){
            FavouriteFoodScreen(navController)
        }

    }
}