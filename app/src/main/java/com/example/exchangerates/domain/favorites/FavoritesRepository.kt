package com.example.exchangerates.domain.favorites

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun getFavorites(): Flow<List<Pair<String, String>>>
    suspend fun getRateByCurrencies(currencyPair: Pair<String, String>): Result<Float?>
    suspend fun deleteFavoritePair(currencyPair: Pair<String, String>)
}