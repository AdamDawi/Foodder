package com.example.foodder.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextColorBox(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .background(color)
        .padding(4.dp)
    ) {
        Text(
            text = text,
            color = Color.DarkGray,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            maxLines = 1
        )
    }
}