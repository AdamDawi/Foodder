package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun FoodCard(
    modifier: Modifier = Modifier,
    foodName: String,
    photo: String
) {
    val colorMatrix = ColorMatrix()
    colorMatrix.setToSaturation(0.5f)
    Card(modifier = modifier
        .fillMaxWidth()
        .height(150.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            AsyncImage(model = photo,
                contentDescription = foodName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .blur(3.dp)
                    .fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(colorMatrix),
            )
                Text(modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(20.dp),
                    text = foodName,
                    fontSize = 30.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
        }
    }
}

