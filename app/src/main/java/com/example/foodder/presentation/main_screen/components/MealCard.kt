package com.example.foodder.presentation.main_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodder.common.Constants
import com.example.foodder.common.TestTags
import com.example.foodder.presentation.main_screen.MainState
import com.example.foodder.presentation.ui.theme.LocalSpacing
import com.example.foodder.presentation.ui.theme.OrangePumpkin
import com.example.foodder.presentation.ui.theme.YellowMaize
import kotlin.math.roundToInt


@OptIn(ExperimentalLayoutApi::class)
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
        if(state.errorMessage.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    transformOrigin = TransformOrigin.Center
                    rotationY = rotateImageY
                    cameraDistance = 30f
                }
                .alpha(alphaImage)
                .testTag(TestTags.MEAL_CARD_FRONT_SIDE)
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
                        .padding(LocalSpacing.current.large)
                        .testTag(TestTags.MEAL_TITLE_ON_CARD),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 2
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
                .testTag(TestTags.MEAL_CARD_BACK_SIDE)
            ) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(12.dp),
                    userScrollEnabled = state.isCardFlipped
                ){
                    item{
                        Text(
                            text = "Information",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(LocalSpacing.current.small),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(LocalSpacing.current.small)
                        ) {
                            TextColorBox(color = YellowMaize, text = state.meal.strArea)
                            Spacer(modifier = Modifier.width(5.dp))
                            TextColorBox(color = YellowMaize, text = state.meal.strCategory)
                        }
                        Text(
                            text = "Ingredients",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(LocalSpacing.current.small),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        FlowRow(modifier = Modifier
                            .fillMaxSize()
                        ) {
                            for(ingredient in state.meal.strIngredients){
                                TextColorBox(
                                    modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                                    color = OrangePumpkin,
                                    text = ingredient
                                )
                            }
                        }

                    }
                }
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