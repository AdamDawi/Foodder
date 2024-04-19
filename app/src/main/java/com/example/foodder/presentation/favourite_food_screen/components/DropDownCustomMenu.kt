package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodder.R
import com.example.foodder.presentation.ui.theme.BackgroundColor
import com.example.foodder.presentation.ui.theme.BlueBlue
import com.example.foodder.presentation.ui.theme.LightBlue

@Composable
fun DropDownCustomMenu(
    modifier: Modifier = Modifier,
    isContextMenuVisible: Boolean,
    items: List<String>,
    selectedItem: Int,
    onDismiss: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .width(250.dp)
            .background(BackgroundColor),
        expanded = isContextMenuVisible,
        onDismissRequest = {onDismiss()}
    ){
        for(i in items.indices){
            key(i) {
                if (i == selectedItem) {
                    DropdownMenuItem(
                        modifier = Modifier
                            .padding(start = 6.dp, end = 6.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LightBlue),
                        text = {
                            Text(
                                text = items[i],
                                color = BlueBlue,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = { onItemClick(i) },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = Icons.Default.Check.name,
                                tint = BlueBlue
                            )
                        }
                    )
                } else {
                    DropdownMenuItem(
                        modifier = Modifier
                            .padding(start = 6.dp, end = 6.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BackgroundColor),
                        text = {
                            Text(
                                text = items[i],
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = { onItemClick(i)},
                    )
                }
            }
        }
    }
}