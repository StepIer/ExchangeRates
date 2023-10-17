package com.example.exchangerates.data.core

import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.api.model.ConvertResponse
import com.example.exchangerates.data.core.api.model.InfoDto
import com.example.exchangerates.data.core.api.model.LatestResponse
import com.example.exchangerates.data.core.api.model.QueryDto
import com.example.exchangerates.data.core.api.model.SymbolsResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeRatesMockApi : ExchangeRatesApi {
    override fun getSymbols(): Call<SymbolsResponse> {
        return object : Call<SymbolsResponse> {
            override fun isExecuted(): Boolean {
                return false
            }

            override fun clone(): Call<SymbolsResponse> {
                return this
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun cancel() {

            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout()
            }

            override fun execute(): Response<SymbolsResponse> {
                return Response.success(symbolsResponse)
            }

            override fun enqueue(callback: Callback<SymbolsResponse>) {
            }
        }
    }

    override fun getRatesByCurrency(currency: String): Call<LatestResponse> {
        return object : Call<LatestResponse> {
            override fun clone(): Call<LatestResponse> {
                return this
            }

            override fun execute(): Response<LatestResponse> {
                return Response.success(latestResponse)
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout()
            }

            override fun enqueue(callback: Callback<LatestResponse>) {
            }
        }
    }

    override fun getRatesByCurrencies(
        from: String,
        to: String,
        amount: Float
    ): Call<ConvertResponse> {
        return object : Call<ConvertResponse> {
            override fun clone(): Call<ConvertResponse> {
                return this
            }

            override fun execute(): Response<ConvertResponse> {
                return Response.success(convertResponse)
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout()
            }

            override fun enqueue(callback: Callback<ConvertResponse>) {
            }
        }
    }

    companion object {
        val symbolsResponse: SymbolsResponse =
            SymbolsResponse(success = true, symbols = mapOf("USD" to "RUB"))
        val latestResponse: LatestResponse = LatestResponse(
            base = "base",
            date = "date",
            rates = mapOf("USD" to 3.020202f),
            success = true,
            timestamp = 1L
        )
        val convertResponse: ConvertResponse = ConvertResponse(
            date = "date",
            info = InfoDto(rate = 3.020202f, timestamp = 1L),
            query = QueryDto(amount = 1f, from = "from", to = "to"),
            result = 3.020202f,
            success = true
        )
    }
}