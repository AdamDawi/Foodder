package com.example.foodder.presentation.favourite_food_screen.common

import com.example.foodder.domain.util.FoodOrder
import com.example.foodder.domain.util.OrderType

data class MenuItem(
    val id: Int,
    val name: String,
    val order: FoodOrder
)

object Items{
    val sortItems: List<MenuItem> = listOf(
        MenuItem(0, "Date Added (oldest first)", FoodOrder.Date(OrderType.Ascending)),
        MenuItem(1, "Date Added (newest first)", FoodOrder.Date(OrderType.Descending)),
        MenuItem(2, "Name (a-z)", FoodOrder.Name(OrderType.Ascending)),
        MenuItem(3, "Name (z-a)", FoodOrder.Name(OrderType.Descending))
    )
}



