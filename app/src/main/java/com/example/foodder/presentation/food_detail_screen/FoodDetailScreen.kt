package com.example.foodder.presentation.food_detail_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import com.example.foodder.common.TopAppBarView

@Composable
fun FoodDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    viewModel.getMealById(id)
    val state = viewModel.state.value
    Scaffold(
        topBar = { TopAppBarView(onBack = {navController.navigateUp()},
            title = state.strMeal)
        },
        containerColor = Color.White
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(10.dp)
        )
        {
            item{
                Text(text = state.strInstructions,
                    color = Color.Black,
                    fontSize = 20.sp)
                //TODO display meal information
            }
        }
    }

}