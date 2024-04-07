package com.example.foodder.presentation.main_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.foodder.presentation.ui.theme.BlueBlue
import com.example.foodder.presentation.ui.theme.OrangePumpkin

@Composable
fun TextWithIconRow(
    modifier: Modifier = Modifier,
    iconColor: Color,
    icon: ImageVector,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()) {
        Icon(
            imageVector = icon,
            contentDescription = "shopping cart",
            tint = iconColor,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(28.dp)
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            color = Color.Black,
            text = text,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun TextColorBoxPreviewOrange() {
    TextWithIconRow(iconColor = OrangePumpkin, icon = Icons.Outlined.ShoppingCart, text = "Pierogies")
}

@Preview
@Composable
fun TextColorBoxPreviewBlue() {
    TextWithIconRow(iconColor = BlueBlue, icon = Icons.Outlined.Info, text = "Pierogies")
}

@Preview
@Composable
fun TextColorBoxPreviewLongBlue() {
    TextWithIconRow(iconColor = BlueBlue, icon = Icons.Outlined.Info, text = "Pierogiesddddddddddddddddddddddddddddddddddddd")
}