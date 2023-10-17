package com.example.exchangerates.domain.currencies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveFavoriteUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(pairFavorites: Pair<String, String>) = withContext(dispatcher) {
        return@withContext currenciesRepository.insertFavorites(pairFavorites)
    }
}