package com.example.foodder.presentation.food_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.foodder.presentation.ui.theme.BlueBlue

@Composable
fun InformationColumn(
    modifier: Modifier = Modifier,
    strArea: String,
    strCategory: String
) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Information",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "info",
                tint = BlueBlue,
                modifier = Modifier
                    .size(28.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                color = Color.Black,
                text = strArea.take(10),
                maxLines = 1,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "info",
                tint = BlueBlue,
                modifier = Modifier
                    .size(28.dp)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Black,
                text = strCategory.take(10),
                maxLines = 1,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }


    }
}