package com.example.exchangerates.data.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryDto(
    @SerialName("amount") val amount: Float,
    @SerialName("from") val from: String,
    @SerialName("to") val to: String,
)
