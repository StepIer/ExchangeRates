package com.example.exchangerates.data.currencies

import com.example.exchangerates.data.core.ExchangeRatesMockApi
import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.data.core.db.model.FavoritePair
import com.example.exchangerates.domain.currencies.CurrenciesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CurrenciesRepositoryTest {

    private var exchangeRatesApi: ExchangeRatesApi = ExchangeRatesMockApi()

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    private lateinit var currenciesRepository: CurrenciesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        currenciesRepository = CurrenciesRepositoryImpl(
            exchangeRatesApi, favoriteDao
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when getCurrencies() called than return result with symbols`() = runTest {
        val result = currenciesRepository.getCurrencies()
        result.onSuccess {
            assert(ExchangeRatesMockApi.symbolsResponse.symbols.keys.toList() == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when getRatesByCurrency() called than return result with rates`() = runTest {
        val result = currenciesRepository.getRatesByCurrency("currency")
        result.onSuccess {
            assert(ExchangeRatesMockApi.latestResponse.rates == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when insertFavorites() called than insert() from dao called with the same params`() =
        runTest {
            currenciesRepository.insertFavorites(
                Pair(
                    favoritePair.firstCurrency,
                    favoritePair.secondCurrency
                )
            )

            Mockito.verify(favoriteDao, Mockito.times(1))
                .insert(favoritePair)
        }

    @Test
    fun `when getFavoritesByFirstCurrency() called than return list favorites values`() = runTest {
        Mockito.`when`(favoriteDao.getByFirstCurrency("USD"))
            .thenReturn(flowOf(listOf(favoritePair)))

        val favorites = currenciesRepository.getFavoritesByFirstCurrency("USD").first()

        assert(listOf(favoritePair.secondCurrency) == favorites)
    }

    companion object {
        val favoritePair = FavoritePair(id = null, firstCurrency = "USD", secondCurrency = "EUR")
    }
}