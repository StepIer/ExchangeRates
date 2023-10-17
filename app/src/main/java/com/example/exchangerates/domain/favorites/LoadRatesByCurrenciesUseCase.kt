package com.example.exchangerates.domain.favorites

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadRatesByCurrenciesUseCase(
    private val favoritesRepository: FavoritesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(currencyPair: Pair<String, String>): Result<Float?> =
        withContext(dispatcher) {
            return@withContext favoritesRepository.getRateByCurrencies(currencyPair)
        }
}