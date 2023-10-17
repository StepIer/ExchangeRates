package com.example.exchangerates.data.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConvertResponse(
    @SerialName("date") val date: String,
    @SerialName("info") val info: InfoDto,
    @SerialName("query") val query: QueryDto,
    @SerialName("result") val result: Float,
    @SerialName("success") val success: Boolean
)