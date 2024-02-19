package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissBg(
    modifier: Modifier = Modifier,
    dismissState: DismissState
) {
    val color = buildColorAnimation(dismissState = dismissState)
    val scale = buildSizeAnimation(dismissState = dismissState)
    Box(modifier = modifier
        .fillMaxSize()
        .padding(bottom = 10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Icon",
            tint = Color.Black,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(35.dp)
                .scale(scale)
        )
    }
}