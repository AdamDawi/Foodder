package com.example.foodder.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodder.common.Constants
import com.example.foodder.presentation.main_screen.components.MealCard


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, bottom = 150.dp, start = 50.dp, end = 50.dp)
                .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS))
                .background(Color.LightGray)
            )
            MealCard(
                state = viewModel.state.value,
                onDrag = { dragAmount ->
                    viewModel.onCardDrag(dragAmount)
                },
                onDragEnd = {
                    viewModel.checkSwipeBounds()
                },
                changeIsCardFrontState = {
                    viewModel.changeIsCardFlippedState()
                }
            )
        }

    }

}


