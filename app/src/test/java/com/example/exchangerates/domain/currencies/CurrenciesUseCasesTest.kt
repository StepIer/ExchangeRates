package com.example.exchangerates.domain.currencies

import com.example.exchangerates.data.core.db.model.FavoritePair
import com.example.exchangerates.data.currencies.CurrenciesRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CurrenciesUseCasesTest {

    private lateinit var getFavoritesByFirstCurrencyUseCase: GetFavoritesByFirstCurrencyUseCase
    private lateinit var loadRatesByCurrencyUseCase: LoadRatesByCurrencyUseCase
    private lateinit var loadCurrenciesUseCase: LoadCurrenciesUseCase
    private lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    @Mock
    private lateinit var currenciesRepository: CurrenciesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getFavoritesByFirstCurrencyUseCase =
            GetFavoritesByFirstCurrencyUseCase(currenciesRepository, UnconfinedTestDispatcher())
        loadRatesByCurrencyUseCase =
            LoadRatesByCurrencyUseCase(currenciesRepository, UnconfinedTestDispatcher())
        loadCurrenciesUseCase =
            LoadCurrenciesUseCase(currenciesRepository, UnconfinedTestDispatcher())
        saveFavoriteUseCase = SaveFavoriteUseCase(currenciesRepository, UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when GetFavoritesByFirstCurrencyUseCase called than return favorites`() = runTest {

        Mockito.`when`(currenciesRepository.getFavoritesByFirstCurrency(favoritePair.firstCurrency))
            .thenReturn(flowOf(listOf(favoritePair.secondCurrency)))

        val favorites =
            getFavoritesByFirstCurrencyUseCase.invoke(favoritePair.firstCurrency).first()

        assert(listOf(CurrenciesRepositoryTest.favoritePair.secondCurrency) == favorites)
    }

    @Test
    fun `when loadCurrenciesUseCase called than return result with symbols`() = runTest {

        Mockito.`when`(currenciesRepository.getCurrencies())
            .thenReturn(Result.success(listOf(favoritePair.firstCurrency)))

        val result = loadCurrenciesUseCase.invoke()

        result.onSuccess {
            assert(listOf(favoritePair.firstCurrency) == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when loadRatesByCurrencyUseCase() called than return result with rates`() = runTest {

        Mockito.`when`(currenciesRepository.getRatesByCurrency(favoritePair.firstCurrency))
            .thenReturn(Result.success(mapOf(favoritePair.secondCurrency to 3.020202f)))

        val result = loadRatesByCurrencyUseCase.invoke(favoritePair.firstCurrency)
        result.onSuccess {
            assert(mapOf(favoritePair.secondCurrency to 3.020202f) == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when saveFavoriteUseCase() called than insertFavorites() from repository called with the same params`() =
        runTest {
            saveFavoriteUseCase.invoke(
                Pair(
                    favoritePair.firstCurrency,
                    favoritePair.secondCurrency
                )
            )

            Mockito.verify(currenciesRepository, Mockito.times(1))
                .insertFavorites(
                    Pair(
                        favoritePair.firstCurrency,
                        favoritePair.secondCurrency
                    )
                )
        }

    companion object {
        val favoritePair = FavoritePair(id = null, firstCurrency = "USD", secondCurrency = "EUR")
    }
}