package com.example.foodder.presentation.food_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.ui.theme.OrangePumpkin

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
            title = state.meal.strMeal,
            containerColor = OrangePumpkin,
            themeColor = Color.White)
        },
        containerColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .clickable { viewModel.changeIsSelected(0) }
                        .weight(1f)
                        .background(OrangePumpkin)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Information",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .clickable { viewModel.changeIsSelected(1) }
                        .weight(1f)
                        .background(Color.White)
                        .padding(10.dp)

                ) {
                    Text(
                        text = "Ingredients",
                        color = OrangePumpkin,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .clickable { viewModel.changeIsSelected(2) }
                        .weight(1f)
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Instruction",
                        color = OrangePumpkin,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
        )
        {
            when (state.isSelected) {
                0 -> {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = state.meal.strArea,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                            Text(
                                text = state.meal.strCategory,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                1 -> {
                    items(state.meal.strIngredients){ item ->
                        Text(text = item,
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
                else -> {
                    item{
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(10.dp)
                        ){
                            Text(text = state.meal.strInstructions,
                                color = Color.Black,
                                fontSize = 20.sp)
                        }
                    }
                }
            }
        }
        }
    }

}