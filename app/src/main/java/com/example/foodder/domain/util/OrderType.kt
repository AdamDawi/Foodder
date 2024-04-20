package com.example.foodder.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}