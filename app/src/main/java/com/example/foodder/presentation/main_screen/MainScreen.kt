package com.example.foodder.presentation.main_screen

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlin.math.roundToInt


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .offset {
                IntOffset(
                    state.imageOffset.x.roundToInt(),
                    state.imageOffset.y.roundToInt()
                )
            }
            .fillMaxSize()
            .padding(bottom = 200.dp)
            .clip(RoundedCornerShape(10.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        viewModel.checkSwipeBounds()
                    },
                    onDragCancel = {
                        viewModel.checkSwipeBounds()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        viewModel.changeImageOffset(dragAmount)
                    }
                )
            }
        ){
            AsyncImage(
                model = state.meal.strMealThumb,
                contentDescription = "Meal image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text =
                    if(state.isLoading) "loading"
                    else if(state.error.isNotEmpty()) "Error: ${state.error}"
                    else state.meal.strMeal,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(30.dp),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

    }

}
