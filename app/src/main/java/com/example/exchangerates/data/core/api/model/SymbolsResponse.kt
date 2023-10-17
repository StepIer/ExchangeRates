package com.example.exchangerates.data.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolsResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("symbols") val symbols: Map<String, String>,
)
