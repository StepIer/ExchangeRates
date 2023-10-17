package com.example.exchangerates.data.currencies

import com.example.exchangerates.data.core.api.ApiException
import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.data.core.db.model.FavoritePair
import com.example.exchangerates.domain.currencies.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrenciesRepositoryImpl(
    private val api: ExchangeRatesApi,
    private val db: FavoriteDao
) : CurrenciesRepository {

    override suspend fun getCurrencies(): Result<List<String>> {
        return try {
            val response = api.getSymbols().execute()

            if (response.isSuccessful) {
                val currencies = response.body()?.symbols?.keys?.toList() ?: emptyList()
                Result.success(currencies)
            } else {
                val errorMessage = "An error occurred: ${response.errorBody()?.string()}"
                Result.failure(ApiException(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRatesByCurrency(currency: String): Result<Map<String, Float>> {
        return try {
            val response = api.getRatesByCurrency(currency).execute()

            if (response.isSuccessful) {
                val currencies = response.body()?.rates ?: emptyMap()
                Result.success(currencies)
            } else {
                val errorMessage = "An error occurred: ${response.errorBody()?.string()}"
                Result.failure(ApiException(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertFavorites(pairFavorites: Pair<String, String>) {
        db.insert(
            FavoritePair(
                firstCurrency = pairFavorites.first,
                secondCurrency = pairFavorites.second
            )
        )
    }

    override suspend fun getFavoritesByFirstCurrency(firstCurrency: String): Flow<List<String>> {
        return db.getByFirstCurrency(firstCurrency)
            .map { list -> list.map { it.secondCurrency } }
    }
}



















