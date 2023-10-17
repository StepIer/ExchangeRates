package com.example.exchangerates.data.favorites

import com.example.exchangerates.data.core.ExchangeRatesMockApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.data.core.db.model.FavoritePair
import com.example.exchangerates.domain.favorites.FavoritesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class FavoritesRepositoryTest {

    private lateinit var favoritesRepository: FavoritesRepository

    @Mock
    private lateinit var favoriteDao: FavoriteDao
    private val exchangeRatesMockApi = ExchangeRatesMockApi()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        favoritesRepository = FavoritesRepositoryImpl(
            favoriteDao, exchangeRatesMockApi
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when getFavorites() called than return list favorites values`() = runTest {
        Mockito.`when`(favoriteDao.getAll()).thenReturn(flowOf(listOf(favoritePair)))

        val favorites = favoritesRepository.getFavorites().first()

        assert(favorites == listOf(Pair(favoritePair.firstCurrency, favoritePair.secondCurrency)))
    }

    @Test
    fun `when getCurrencies() called than return result with rate`() = runTest {
        val result = favoritesRepository.getRateByCurrencies(
            Pair(
                favoritePair.firstCurrency,
                favoritePair.secondCurrency
            )
        )

        result.onSuccess {
            assert(ExchangeRatesMockApi.convertResponse.result == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when deleteFavoritePair() called than delete() from dao called with the same params`() =
        runTest {
            favoritesRepository.deleteFavoritePair(
                Pair(
                    favoritePair.firstCurrency,
                    favoritePair.secondCurrency
                )
            )

            Mockito.verify(favoriteDao, times(1))
                .delete(favoritePair.firstCurrency, favoritePair.secondCurrency)
        }

    companion object {
        val favoritePair = FavoritePair(id = null, firstCurrency = "USD", secondCurrency = "EUR")
    }
}