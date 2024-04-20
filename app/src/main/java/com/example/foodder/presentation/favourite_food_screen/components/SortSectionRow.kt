package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodEvent
import com.example.foodder.presentation.favourite_food_screen.FavouriteFoodViewModel
import com.example.foodder.presentation.favourite_food_screen.common.Items
import com.example.foodder.presentation.ui.theme.BackgroundColor
import com.example.foodder.presentation.ui.theme.BlueBlue
import com.example.foodder.presentation.ui.theme.LightBlue

@Composable
fun SortSectionRow(
    modifier: Modifier = Modifier,
    viewModel: FavouriteFoodViewModel
) {
    //sort variables
    var isSortMenuVisible by remember {
        mutableStateOf(false)
    }
    var selectedSortItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val rotationSort = buildRotationAnimation(isMenuVisible = isSortMenuVisible)
    val colorSort = buildColorAnimationBlueToBlack(isMenuVisible = isSortMenuVisible)

    //filter variables
    var isFilterMenuVisible by remember {
        mutableStateOf(false)
    }
    var selectedFilterItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val rotationFilter = buildRotationAnimation(isMenuVisible = isFilterMenuVisible)
    val colorFilter = buildColorAnimationBlueToBlack(isMenuVisible = isFilterMenuVisible)


    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .pointerInput(true) {
                    detectTapGestures(onTap = {
                        isFilterMenuVisible = true
                    })
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filters",
                fontWeight = FontWeight.Bold,
                color = colorFilter,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = Icons.Default.KeyboardArrowDown.name,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(rotationFilter),
                tint = colorFilter
            )
            DropdownMenu(
                modifier = modifier
                    .width(220.dp)
                    .heightIn(max = 300.dp)
                    .background(BackgroundColor),
                expanded = isFilterMenuVisible,
                onDismissRequest = { isFilterMenuVisible = false }
            ) {
                for (category in viewModel.state.value.categories) {
                    key(category.id) {
                        val isSelected = category.id == selectedFilterItem
                        DropdownMenuItem(
                            modifier = Modifier
                                .padding(start = 6.dp, end = 6.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) LightBlue else BackgroundColor),
                            text = {
                                Text(
                                    text = viewModel.state.value.categories[category.id].strCategory,
                                    color = if (isSelected) BlueBlue else Color.Black,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                selectedFilterItem = category.id
                                viewModel.onEvent(FavouriteFoodEvent.Filter(category.strCategory))
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .pointerInput(true) {
                    detectTapGestures(onTap = {
                        isSortMenuVisible = true
                    })
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort by",
                fontWeight = FontWeight.Bold,
                color = colorSort,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = Icons.Default.KeyboardArrowDown.name,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(rotationSort),
                tint = colorSort
            )
            DropDownCustomMenu(
                isContextMenuVisible = isSortMenuVisible,
                selectedItem = selectedSortItem,
                onItemClick = { selectedSortItem = it },
                onDismiss = { isSortMenuVisible = false },
                items = Items.sortItems,
                onEventOrder = { viewModel.onEvent(FavouriteFoodEvent.Order(it.foodOrder)) }
            )
        }
    }
}



