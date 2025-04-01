package com.example.recipefeed.feature

data class UiIngredient(
    val name: String = "",
    val amount: Double? = null,
    val unit: String = "", // Добавляем единицу измерения
    val possibleUnits: List<String> = emptyList() // Возможные единицы измерения
)