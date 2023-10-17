package com.example.exchangerates.data.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoDto(
    @SerialName("rate") val rate: Float,
    @SerialName("timestamp") val timestamp: Long,
)