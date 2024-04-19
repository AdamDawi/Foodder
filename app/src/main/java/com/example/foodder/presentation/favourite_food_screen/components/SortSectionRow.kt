package com.example.foodder.presentation.favourite_food_screen.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortSectionRow(
    modifier: Modifier = Modifier,
    sortItems: List<String> = listOf(
        "Date Added (oldest first)",
        "Date Added (newest first)",
        "Name (a-z)",
        "Name (z-a)"
        ),
    filterItems: List<String> = listOf(
        "Default",
        "Category"
    )
) {
    //sort variables
    var isSortMenuVisible by remember {
        mutableStateOf(false)
    }
    var selectedSortItem by remember {
        mutableIntStateOf(0)
    }
    val rotationSort = buildRotationAnimation(isMenuVisible = isSortMenuVisible)
    val colorSort = buildColorAnimationBlueToBlack(isMenuVisible = isSortMenuVisible)

    //filter variables
    var isFilterMenuVisible by remember {
        mutableStateOf(false)
    }
    var selectedFilterItem by remember {
        mutableIntStateOf(0)
    }
    val rotationFilter = buildRotationAnimation(isMenuVisible = isFilterMenuVisible)
    val colorFilter = buildColorAnimationBlueToBlack(isMenuVisible = isFilterMenuVisible)
    

    Row(modifier = modifier
        .fillMaxWidth()
    ){
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
                Text(text = "Filters",
                    fontWeight = FontWeight.Bold,
                    color = colorFilter,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Icon(imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = Icons.Default.KeyboardArrowDown.name ,
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotationFilter),
                    tint = colorFilter,
                )
                DropDownCustomMenu(
                    isContextMenuVisible = isFilterMenuVisible,
                    items = filterItems,
                    selectedItem = selectedFilterItem,
                    onItemClick = {selectedFilterItem=it},
                    onDismiss = {isFilterMenuVisible=false}
                )
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
                    tint = colorSort,
                )
                DropDownCustomMenu(
                    isContextMenuVisible = isSortMenuVisible,
                    items = sortItems,
                    selectedItem = selectedSortItem,
                    onItemClick = {selectedSortItem=it},
                    onDismiss = {isSortMenuVisible=false}
                )
            }
    }
}



