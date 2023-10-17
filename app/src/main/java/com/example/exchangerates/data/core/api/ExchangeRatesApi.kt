package com.example.exchangerates.data.core.api

import com.example.exchangerates.data.core.api.model.ConvertResponse
import com.example.exchangerates.data.core.api.model.LatestResponse
import com.example.exchangerates.data.core.api.model.SymbolsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("symbols")
    fun getSymbols(): Call<SymbolsResponse>

    @GET("latest")
    fun getRatesByCurrency(@Query("base") currency: String): Call<LatestResponse>

    @GET("convert")
    fun getRatesByCurrencies(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Float = 1f
    ): Call<ConvertResponse>
}