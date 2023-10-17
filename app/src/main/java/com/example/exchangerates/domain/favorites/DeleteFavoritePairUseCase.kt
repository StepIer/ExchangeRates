package com.example.exchangerates.domain.favorites

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFavoritePairUseCase(
    private val favoritesRepository: FavoritesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(currencyPair: Pair<String, String>) = withContext(dispatcher) {
        favoritesRepository.deleteFavoritePair(currencyPair)
    }
}