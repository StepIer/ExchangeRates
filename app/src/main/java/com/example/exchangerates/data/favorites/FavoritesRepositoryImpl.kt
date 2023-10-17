package com.example.exchangerates.data.favorites

import com.example.exchangerates.data.core.api.ApiException
import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.domain.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val db: FavoriteDao,
    private val api: ExchangeRatesApi
) : FavoritesRepository {
    override suspend fun getFavorites(): Flow<List<Pair<String, String>>> {
        return db.getAll().map { list -> list.map { Pair(it.firstCurrency, it.secondCurrency) } }
    }

    override suspend fun getRateByCurrencies(currencyPair: Pair<String, String>): Result<Float?> {
        return try {
            val response =
                api.getRatesByCurrencies(from = currencyPair.first, to = currencyPair.second)
                    .execute()

            if (response.isSuccessful) {
                val currencies = response.body()?.result
                Result.success(currencies)
            } else {
                val errorMessage = "An error occurred: ${response.errorBody()?.string()}"
                Result.failure(ApiException(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFavoritePair(currencyPair: Pair<String, String>) {
        db.delete(currencyPair.first, currencyPair.second)
    }
}