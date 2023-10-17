package com.example.exchangerates.domain.currencies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadRatesByCurrencyUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(currency: String): Result<Map<String, Float>> = withContext(dispatcher) {
        return@withContext currenciesRepository.getRatesByCurrency(currency)
    }
}