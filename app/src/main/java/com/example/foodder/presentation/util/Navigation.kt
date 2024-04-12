package com.example.foodder.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodder.common.SplashScreen
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodScreen
import com.example.foodder.presentation.food_detail_screen.FoodDetailScreen
import com.example.foodder.presentation.main_screen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.SplashScreen.route
        ){
        composable(route = Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }
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