package com.example.foodder.presentation.main_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
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
import coil.compose.AsyncImage
import com.example.foodder.common.Constants
import com.example.foodder.presentation.main_screen.MainViewModel
import kotlin.math.roundToInt

@Composable
fun MealCard(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.value

    Box(modifier = modifier
        .offset {
            IntOffset(
                state.imageOffset.x.roundToInt(),
                state.imageOffset.y.roundToInt()
            )
        }
        .fillMaxSize()
        .padding(top = 50.dp, bottom = 250.dp, start = 50.dp, end = 50.dp)
        .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_SIZE))
        .border(
            BorderStroke(3.dp, state.cardBorderColor),
            shape = RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_SIZE)
        )
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
                    viewModel.changeImageOffsetState(dragAmount)
                }
            )
        }
    ){
        if(!state.isLoading && state.error.isEmpty()){
            AsyncImage(
                model = state.meal.strMealThumb,
                contentDescription = "Meal image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = state.meal.strMeal,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(30.dp),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }else if(state.error.isNotEmpty()){
            Text(
                text = state.error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                color = Color.Magenta,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}