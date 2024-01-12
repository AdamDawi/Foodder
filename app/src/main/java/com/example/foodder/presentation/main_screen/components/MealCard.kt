package com.example.foodder.presentation.main_screen.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodder.common.Constants
import com.example.foodder.presentation.main_screen.MainState
import kotlin.math.roundToInt


@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    state: MainState,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    changeIsCardFrontState: () -> Unit
) {
    val alphaImage = animateFloatAsState(
        targetValue = if(!state.isCardFlipped) 1f else 0f,
        animationSpec = tween(
            durationMillis = Constants.FLIP_CARD_ANIMATION_TIME-150,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val alphaDescription = animateFloatAsState(
        targetValue = if(state.isCardFlipped) 1f else 0f,
        animationSpec = tween(
            durationMillis = Constants.FLIP_CARD_ANIMATION_TIME-150,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val rotateImageY = animateFloatAsState(
        targetValue = if(!state.isCardFlipped) 0f else 180f,
        animationSpec = tween(
            durationMillis = Constants.FLIP_CARD_ANIMATION_TIME,
                    easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val rotateDescriptionY = animateFloatAsState(
        targetValue = if(!state.isCardFlipped) -180f else 0f,
        animationSpec = tween(
            durationMillis = Constants.FLIP_CARD_ANIMATION_TIME,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val cardOffset by animateOffsetAsState(
        targetValue = state.cardOffset,
        label = ""
    )

    Box(modifier = modifier
        .offset {
            IntOffset(cardOffset.x.roundToInt(),
                cardOffset.y.roundToInt()
            )
        }
        .fillMaxSize()
        .padding(top = 150.dp, bottom = 150.dp, start = 50.dp, end = 50.dp)
        .border(
            BorderStroke(4.dp, state.cardBorderColor),
            shape = RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS)
        )
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    onDragEnd()

                },
                onDragCancel = onDragEnd,
                onDrag = { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            )
        }
        //clicking without ripple
        .pointerInput(Unit) {
            detectTapGestures {
                changeIsCardFrontState()
            }
        }
    ){
        if(!state.isLoading && state.errorMessage.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                    rotationY = rotateImageY.value
                    cameraDistance = 30f
                }
                .alpha(alphaImage.value)
                .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS))
            ){
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
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                    rotationY = rotateDescriptionY.value
                    cameraDistance = 30f
                }
                .alpha(alphaDescription.value)
                .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS))
                .background(Color.LightGray)

            ) {
                Text(text = "Description",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(30.dp)
                )
            }
        }
        else if(state.errorMessage.isNotEmpty()){
            Text(
                text = state.errorMessage,
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