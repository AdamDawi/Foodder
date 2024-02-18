package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodder.presentation.favourite_food_screen.components.FoodCard
import com.example.foodder.presentation.favourite_food_screen.components.TopAppBarView

@Composable
fun FavouriteFoodScreen(
    viewModel: FavouriteFoodViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBarView { navController.navigateUp() }
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it),
            contentPadding = PaddingValues(10.dp)
        ){
            items(state.meals.size){ id ->
                FoodCard(
                    modifier = Modifier.padding(bottom = 10.dp),
                    foodName = state.meals[id].strMeal,
                    photo = state.meals[id].strMealThumb
                )
            }
        }
    }
}

@Preview
@Composable
fun FavouriteFoodScreenPreview() {
    FavouriteFoodScreen(navController = rememberNavController())
}