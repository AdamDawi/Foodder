package com.example.foodder.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodder.common.Constants
import com.example.foodder.common.TestTags
import com.example.foodder.presentation.main_screen.components.AnimatedCounter
import com.example.foodder.presentation.main_screen.components.MealCard
import com.example.foodder.presentation.main_screen.components.buildShakeAnimation
import com.example.foodder.presentation.main_screen.components.shake
import com.example.foodder.presentation.ui.theme.BackgroundColor
import com.example.foodder.presentation.ui.theme.BlueBlue
import com.example.foodder.presentation.ui.theme.GreenBlue
import com.example.foodder.presentation.ui.theme.RedPink
import com.example.foodder.presentation.util.Screen

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val shakeRightAnimation = buildShakeAnimation(isShaking = state.isSwipeToRightShaking)
    val shakeLeftAnimation = buildShakeAnimation(isShaking = state.isSwipeToLeftShaking)

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        containerColor = BackgroundColor,
        floatingActionButton =
        {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.FavouriteFoodScreen.route) },
                containerColor = BlueBlue
            ) {
                Icon(imageVector = Icons.Default.List,
                    contentDescription = "List of favourite food",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(45.dp)
                )
            }
        }
    ) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(it)
    ){
        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 30.dp, start = 30.dp, bottom = 30.dp)
            .height(60.dp)
            .width(90.dp)
            .clip(RoundedCornerShape(topStart = 100f, bottomStart = 100f))
            .background(GreenBlue)
        ){
            Row(modifier = Modifier
                .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    modifier = Modifier
                        .size(28.dp)
                        .shake(shakeRightAnimation)
                )
                AnimatedCounter(
                    count = viewModel.rightSwipeCounter.intValue,
                    textModifier = Modifier.testTag(TestTags.LIKE_COUNTER)
                )
            }
        }
        Box(modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 30.dp, end = 30.dp, bottom = 30.dp)
            .height(60.dp)
            .width(90.dp)
            .clip(RoundedCornerShape(topEnd = 100f, bottomEnd = 100f))
            .background(RedPink)
        ){
            Row(modifier = Modifier
                .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AnimatedCounter(
                    count = viewModel.leftSwipeCounter.intValue,
                    textModifier = Modifier.testTag(TestTags.DELETE_COUNTER)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(28.dp)
                        .shake(shakeLeftAnimation)
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp, bottom = 150.dp, start = 50.dp, end = 50.dp)
            .clip(RoundedCornerShape(Constants.CARD_ROUNDED_CORNER_RADIUS))
            .background(Color.LightGray)
        )
        MealCard(
            state = viewModel.state.value,
            onDrag = { dragAmount ->
                viewModel.onEvent(MainEvent.OnCardDrag(dragAmount))
            },
            onDragEnd = {
                viewModel.onEvent(MainEvent.OnCardDragEnd)
            },
            onClicked = {
                viewModel.onEvent(MainEvent.OnCardClicked)
            },
            modifier = Modifier.testTag(TestTags.MEAL_CARD)
        )
    }
    }
}