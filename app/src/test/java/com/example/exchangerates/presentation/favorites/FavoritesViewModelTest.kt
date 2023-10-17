package com.example.exchangerates.presentation.favorites

import com.example.exchangerates.domain.favorites.DeleteFavoritePairUseCase
import com.example.exchangerates.domain.favorites.GetFavoriteUseCase
import com.example.exchangerates.domain.favorites.LoadRatesByCurrenciesUseCase
import com.example.exchangerates.presentation.favorites.model.CurrencyRate
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.orbitmvi.orbit.test.test

class FavoritesViewModelTest {

    @Mock
    private lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Mock
    private lateinit var loadRatesByCurrenciesUseCase: LoadRatesByCurrenciesUseCase

    @Mock
    private lateinit var deleteFavoritePairUseCase: DeleteFavoritePairUseCase

    private lateinit var favoritesViewModel: FavoritesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        runTest {
            Mockito.`when`(getFavoriteUseCase.invoke())
                .thenReturn(flowOf(listOf(Pair("USD", "EUR"))))
            Mockito.`when`(loadRatesByCurrenciesUseCase.invoke(Pair("USD", "EUR")))
                .thenReturn(Result.success(3.020202f))

            favoritesViewModel = FavoritesViewModel(
                getFavoriteUseCase,
                loadRatesByCurrenciesUseCase,
                deleteFavoritePairUseCase
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test Action FavoriteImageClicked`() = runTest() {

        favoritesViewModel.test(this, state) {
            expectInitialState()
            containerHost.sendAction(FavoritesViewModel.Action.FavoriteImageClicked(
                CurrencyRate("USD", "EUR", null)
            ))
        }

        Mockito.verify(deleteFavoritePairUseCase, Mockito.times(1))
            .invoke(Pair("USD", "EUR"))
    }

    companion object {
        val state = FavoritesViewModel.State(
            currencyPairs = emptyList()
        )
    }
}