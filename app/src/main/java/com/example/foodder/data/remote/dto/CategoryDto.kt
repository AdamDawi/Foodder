package com.example.foodder.data.remote.dto

import com.example.foodder.domain.model.Category

data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)

fun CategoryDto.toCategory(): Category{
    return Category(id = idCategory.toInt(), strCategory = strCategory)
}