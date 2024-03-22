package com.example.foodder.presentation.food_detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowRight
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.foodder.presentation.ui.theme.OrangePumpkin

@Composable
fun IngredientRow(
    modifier: Modifier = Modifier,
    uriHandler: UriHandler,
    ingredient: String,
    measurement: String
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(top = 5.dp, bottom = 5.dp)
        .clip(RoundedCornerShape(18.dp))
        .clickable {
            uriHandler.openUri("https://www.google.com/search?q=${ingredient}")
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.9f)) {
            Icon(
                imageVector = Icons.TwoTone.ShoppingCart,
                contentDescription = "shopping cart",
                tint = OrangePumpkin,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(28.dp)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Black,
                text = "$ingredient - $measurement",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            imageVector = Icons.TwoTone.KeyboardArrowRight,
            contentDescription = "arrow",
            tint = OrangePumpkin,
            modifier = Modifier.size(40.dp)
        )
    }
}