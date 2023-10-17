package com.example.exchangerates.domain.currencies

import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {

    suspend fun getCurrencies(): Result<List<String>>
    suspend fun getRatesByCurrency(currency: String): Result<Map<String, Float>>
    suspend fun insertFavorites(pairFavorites: Pair<String, String>)
    suspend fun getFavoritesByFirstCurrency(firstCurrency: String): Flow<List<String>>
}