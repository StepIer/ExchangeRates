package com.example.exchangerates.domain.currencies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetFavoritesByFirstCurrencyUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(firstCurrency: String): Flow<List<String>> = withContext(dispatcher) {
        return@withContext currenciesRepository.getFavoritesByFirstCurrency(firstCurrency)
    }
}