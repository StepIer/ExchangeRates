package com.example.exchangerates.domain.favorites

import com.example.exchangerates.data.core.db.model.FavoritePair
import com.example.exchangerates.data.favorites.FavoritesRepositoryTest
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
class FavoritesUseCasesTest {

    private lateinit var deleteFavoritePairUseCase: DeleteFavoritePairUseCase
    private lateinit var getFavoriteUseCase: GetFavoriteUseCase
    private lateinit var loadRatesByCurrenciesUseCase: LoadRatesByCurrenciesUseCase

    @Mock
    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deleteFavoritePairUseCase =
            DeleteFavoritePairUseCase(favoritesRepository, UnconfinedTestDispatcher())
        getFavoriteUseCase =
            GetFavoriteUseCase(favoritesRepository, UnconfinedTestDispatcher())
        loadRatesByCurrenciesUseCase =
            LoadRatesByCurrenciesUseCase(favoritesRepository, UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when getFavoriteUseCase() called than return list favorites values`() = runTest {
        Mockito.`when`(favoritesRepository.getFavorites()).thenReturn(
            flowOf(listOf(Pair(favoritePair.firstCurrency, favoritePair.secondCurrency)))
        )

        val favorites = getFavoriteUseCase.invoke().first()

        assert(
            favorites == listOf(
                Pair(
                    favoritePair.firstCurrency,
                    favoritePair.secondCurrency
                )
            )
        )
    }

    @Test
    fun `when loadRatesByCurrenciesUseCase() called than return result with rate`() = runTest {

        Mockito.`when`(
            favoritesRepository.getRateByCurrencies(
                Pair(
                    FavoritesRepositoryTest.favoritePair.firstCurrency,
                    FavoritesRepositoryTest.favoritePair.secondCurrency
                )
            )
        ).thenReturn(Result.success(rate))

        val result = loadRatesByCurrenciesUseCase.invoke(
            Pair(
                favoritePair.firstCurrency,
                favoritePair.secondCurrency
            )
        )

        result.onSuccess {
            assert(rate == it)
        }.onFailure {
            assert(false)
        }
    }

    @Test
    fun `when deleteFavoritePairUseCase() called than deleteFavoritePair() from repository called with the same params`() =
        runTest {
            deleteFavoritePairUseCase.invoke(
                Pair(
                    FavoritesRepositoryTest.favoritePair.firstCurrency,
                    FavoritesRepositoryTest.favoritePair.secondCurrency
                )
            )

            Mockito.verify(favoritesRepository, Mockito.times(1))
                .deleteFavoritePair(
                    Pair(
                        favoritePair.firstCurrency,
                        favoritePair.secondCurrency
                    )
                )
        }

    companion object {
        val favoritePair = FavoritePair(id = null, firstCurrency = "USD", secondCurrency = "EUR")
        const val rate = 3.020202f
    }
}