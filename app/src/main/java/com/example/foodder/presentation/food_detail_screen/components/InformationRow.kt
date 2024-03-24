package com.example.foodder.presentation.food_detail_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.foodder.presentation.ui.theme.BlueBlue

@Composable
fun InformationRow(
    modifier: Modifier = Modifier,
    strArea: String,
    strCategory: String
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.TwoTone.Info,
            contentDescription = "info",
            tint = BlueBlue,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(28.dp)
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            color = Color.Black,
            text = strArea,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.TwoTone.Info,
            contentDescription = "info",
            tint = BlueBlue,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(28.dp)
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            color = Color.Black,
            text = strCategory,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}