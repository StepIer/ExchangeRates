package com.example.exchangerates.domain.currencies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(): Result<List<String>> = withContext(dispatcher) {
        return@withContext currenciesRepository.getCurrencies()
    }
}