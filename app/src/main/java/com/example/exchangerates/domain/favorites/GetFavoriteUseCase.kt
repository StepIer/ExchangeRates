package com.example.exchangerates.domain.favorites

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetFavoriteUseCase(
    private val favoritesRepository: FavoritesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(): Flow<List<Pair<String, String>>> = withContext(dispatcher) {
        return@withContext favoritesRepository.getFavorites()
    }
}