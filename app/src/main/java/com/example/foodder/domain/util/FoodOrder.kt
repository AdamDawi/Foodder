package com.example.foodder.domain.util

sealed class FoodOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): FoodOrder(orderType)
    class Date(orderType: OrderType): FoodOrder(orderType)
}