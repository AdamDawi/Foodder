package com.example.foodder.common

import androidx.compose.ui.unit.dp
import com.example.foodder.domain.model.Meal

object Constants {
    const val DB_NAME = "food_database"
    const val BASE_URL = "https://www.themealdb.com/api/json/"
    val CARD_ROUNDED_CORNER_RADIUS = 12.dp
    const val GOOGLE_URL = "https://www.google.com/search?q="
    val DUMMY_DATA = Meal(
        "British",
        "Seafood",
        "",
        "Kedgeree",
        "https:/www.themealdb.com/images/media/meals/utxqpt1511639216.jpg",
        mutableListOf(
            "Smoked Haddock",
            "Bay Leaves",
            "Milk",
            "Eggs",
            "Parsley",
            "Coriander",
            "Vegetable Oil",
            "Onion",
            "Coriander",
            "Curry Powder",
            "Rice",
            "Smoked Haddock",
            "Bay Leaves",
            "Milk",
            "Eggs",
            "Parsley",
            "Coriander",
            "Vegetable Oil",
            "Onion",
            "Coriander",
            "Curry Powder",
            "Rice"
        )
    )
}