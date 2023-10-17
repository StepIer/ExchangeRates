package com.example.exchangerates.presentation.favorites.model

data class CurrencyRate(
    val firstCurrency: String,
    val secondCurrency: String,
    val rate: Float?
)
