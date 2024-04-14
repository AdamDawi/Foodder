package com.example.foodder.presentation.main_screen

import androidx.compose.ui.geometry.Offset

sealed class MainEvent {
    data class OnCardDrag(val dragAmount: Offset): MainEvent()
    data object OnCardClicked: MainEvent()
    data object OnCardDragEnd: MainEvent()
}