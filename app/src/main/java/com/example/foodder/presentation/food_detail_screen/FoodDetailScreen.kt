package com.example.foodder.presentation.food_detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun FoodDetailScreen(
    id: Int,
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Text(text = id.toString(),
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black)
        //TODO display meal information
    }
}