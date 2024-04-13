package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buildColorAnimation(dismissState: DismissState) =
    animateColorAsState(
        targetValue = if(dismissState.targetValue == DismissValue.DismissedToStart)
            Color.Red else Color.LightGray,
        label = "colorAnimation"
    ).value

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buildSizeAnimation(dismissState: DismissState) =
    animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.6f else 1f,
        label = "sizeAnimation"
    ).value