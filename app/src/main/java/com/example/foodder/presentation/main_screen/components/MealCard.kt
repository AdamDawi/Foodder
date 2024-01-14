package com.example.foodder.presentation.main_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodder.common.Constants
import com.example.foodder.presentation.main_screen.MainState
import com.example.foodder.presentation.ui.theme.LocalSpacing
import kotlin.math.roundToInt


@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    state: MainState,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onCardClicked: () -> Unit
) {
    val alphaImage = buildAlphaAnimation(targetValue = if(!state.isCardFlipped) 1f else 0f)
    val alphaDescription = buildAlphaAnimation(targetValue = if(state.isCardFlipped) 1f else 0f)
    val rotateImageY = buildRotationAnimation(targetValue = if(!state.isCardFlipped) 0f else 180f)
    val rotateDescriptionY = buildRotationAnimation(targetValue = if(!state.isCardFlipped) -180f else 0f)
    val animatedCardOffset = buildOffsetAnimation(targetValue = state.cardOffset)

    Box(modifier = modifier
        .offset {
            IntOffset(
                animatedCardOffset.x.roundToInt(),
                animatedCardOffset.y.roundToInt()
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
                onDragEnd = onDragEnd,
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
                onCardClicked()
            }
        }
    ){
        if(!state.isLoading && state.errorMessage.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                    rotationY = rotateImageY
                    cameraDistance = 30f
                }
                .alpha(alphaImage)
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
                        .padding(LocalSpacing.current.large),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                    rotationY = rotateDescriptionY
                    cameraDistance = 30f
                }
                .alpha(alphaDescription)
                .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS))
                .background(Color.LightGray)
            ) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(LocalSpacing.current.large),
                    userScrollEnabled = state.isCardFlipped
                ){
                    item{
                        Text(text = state.meal.strInstructions,
                            modifier = Modifier
                                .fillMaxSize(),
                            color = Color.DarkGray,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Text(
                    text = "Instruction",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(LocalSpacing.current.small),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow down",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(LocalSpacing.current.small)
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