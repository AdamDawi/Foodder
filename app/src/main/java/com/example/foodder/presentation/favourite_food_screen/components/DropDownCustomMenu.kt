package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodEvent
import com.example.foodder.presentation.favourite_food_screen.common.MenuItem
import com.example.foodder.presentation.ui.theme.BackgroundColor
import com.example.foodder.presentation.ui.theme.BlueBlue
import com.example.foodder.presentation.ui.theme.LightBlue

@Composable
fun DropDownCustomMenu(
    modifier: Modifier = Modifier,
    isContextMenuVisible: Boolean,
    items: List<MenuItem>,
    selectedItem: Int,
    onDismiss: () -> Unit,
    onItemClick: (Int) -> Unit,
    onEventOrder: (FavouriteFoodEvent.Order) -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .width(220.dp)
            .background(BackgroundColor),
        expanded = isContextMenuVisible,
        onDismissRequest = { onDismiss() }
    ) {
        for (el in items) {
            key(el.id) {
                val isSelected = el.id == selectedItem
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(start = 6.dp, end = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) LightBlue else BackgroundColor),
                    text = {
                        Text(
                            text = items[el.id].name,
                            color = if (isSelected) BlueBlue else Color.Black,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onItemClick(el.id)
                        onEventOrder(FavouriteFoodEvent.Order(el.order))
                    }
                )
            }
        }
    }
}
