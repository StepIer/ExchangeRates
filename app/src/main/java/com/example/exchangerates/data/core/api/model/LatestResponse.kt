package com.example.exchangerates.data.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestResponse(
    @SerialName("base") val base: String,
    @SerialName("date") val date: String,
    @SerialName("rates") val rates: Map<String, Float>,
    @SerialName("success") val success: Boolean,
    @SerialName("timestamp") val timestamp: Long
)