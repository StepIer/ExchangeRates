package com.example.exchangerates.presentation.currencies

import com.example.exchangerates.domain.currencies.GetFavoritesByFirstCurrencyUseCase
import com.example.exchangerates.domain.currencies.LoadCurrenciesUseCase
import com.example.exchangerates.domain.currencies.LoadRatesByCurrencyUseCase
import com.example.exchangerates.domain.currencies.SaveFavoriteUseCase
import com.example.exchangerates.domain.favorites.DeleteFavoritePairUseCase
import com.example.exchangerates.presentation.currencies.model.FilterType
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.orbitmvi.orbit.test.test

class CurrenciesViewModelTest {

    @Mock
    private lateinit var loadCurrenciesUseCase: LoadCurrenciesUseCase

    @Mock
    private lateinit var loadRatesByCurrencyUseCase: LoadRatesByCurrencyUseCase

    @Mock
    private lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    @Mock
    private lateinit var getFavoritesByFirstCurrencyUseCase: GetFavoritesByFirstCurrencyUseCase

    @Mock
    private lateinit var deleteFavoritePairUseCase: DeleteFavoritePairUseCase

    private lateinit var currenciesViewModel: CurrenciesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        runTest {
            Mockito.`when`(loadCurrenciesUseCase.invoke())
                .thenReturn(Result.success(listOf(currency)))

            currenciesViewModel = CurrenciesViewModel(
                loadCurrenciesUseCase,
                loadRatesByCurrencyUseCase,
                saveFavoriteUseCase,
                getFavoritesByFirstCurrencyUseCase,
                deleteFavoritePairUseCase
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test Action CurrencyChosen`() = runTest() {

        Mockito.`when`(getFavoritesByFirstCurrencyUseCase.invoke(currency))
            .thenReturn(flowOf(listOf("USD, EUR")))
        Mockito.`when`(loadRatesByCurrencyUseCase.invoke(currency))
            .thenReturn(Result.success(mapOf("USD" to 3.020202f)))

        currenciesViewModel.test(this, state) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.CurrencyChosen(currency))

            expectState { copy(textMenu = currency, isMenuExpanded = false) }
            expectState { copy(loading = true, currentCurrency = currency) }
            expectState { copy(loading = false) }
            expectState { copy(listFavorites = listOf("USD, EUR")) }
            expectState { copy(rates = mapOf("USD" to 3.020202f)) }
        }
    }

    @Test
    fun `test Action FavoriteImageClicked when listFavorites contains than called deleteFavoritePairUseCase`() =
        runTest() {

            currenciesViewModel.test(
                this, state.copy(
                    currentCurrency = currency, listFavorites = listOf(
                        currency
                    )
                )
            ) {
                expectInitialState()
                containerHost.sendAction(CurrenciesViewModel.Action.FavoriteImageClicked(currency))
            }

            Mockito.verify(deleteFavoritePairUseCase, Mockito.times(1))
                .invoke(Pair(currency, currency))
        }

    @Test
    fun `test Action FavoriteImageClicked when listFavorites no contains than called saveFavoriteUseCase`() =
        runTest() {

            currenciesViewModel.test(
                this, state.copy(
                    currentCurrency = currency
                )
            ) {
                expectInitialState()
                containerHost.sendAction(CurrenciesViewModel.Action.FavoriteImageClicked(currency))


            }
            Mockito.verify(saveFavoriteUseCase, Mockito.times(1))
                .invoke(Pair(currency, currency))
        }

    @Test
    fun `test Action FiltersButtonClicked`() = runTest {

        currenciesViewModel.test(this, state) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FiltersButtonClicked)

            expectState { copy(isVisibleDialog = true) }
        }
    }

    @Test
    fun `test Action FilterDialogClosed when filterType AZ`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(rates = mapOf(pairC3, pairB2, pairA1), isVisibleDialog = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(FilterType.AZ))

            expectState { copy(rates = mapOf(pairA1, pairB2, pairC3), isVisibleDialog = false) }
        }
    }

    @Test
    fun `test Action FilterDialogClosed when filterType ZA`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(rates = mapOf(pairA1, pairB2, pairC3), isVisibleDialog = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(FilterType.ZA))

            expectState { copy(rates = mapOf(pairC3, pairB2, pairA1), isVisibleDialog = false) }
        }
    }

    @Test
    fun `test Action FilterDialogClosed when filterType ASC`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(rates = mapOf(pairC3, pairB2, pairA1), isVisibleDialog = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(FilterType.ASC))

            expectState { copy(rates = mapOf(pairA1, pairB2, pairC3), isVisibleDialog = false) }
        }
    }

    @Test
    fun `test Action FilterDialogClosed when filterType DESC`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(rates = mapOf(pairA1, pairB2, pairC3), isVisibleDialog = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(FilterType.DESC))

            expectState { copy(rates = mapOf(pairC3, pairB2, pairA1), isVisibleDialog = false) }
        }
    }

    @Test
    fun `test Action FilterDialogClosed when filterType null`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(rates = mapOf(pairA1, pairB2, pairC3), isVisibleDialog = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(null))

            expectState { copy(isVisibleDialog = false) }
        }
    }

    @Test
    fun `test Action MenuDismissRequested`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(isMenuExpanded = true)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.MenuDismissRequested)

            expectState { copy(isMenuExpanded = false) }
        }
    }

    @Test
    fun `test Action MenuExpandedChanged`() = runTest {

        currenciesViewModel.test(
            this,
            state.copy(isMenuExpanded = false)
        ) {
            expectInitialState()
            containerHost.sendAction(CurrenciesViewModel.Action.MenuExpandedChanged)

            expectState { copy(isMenuExpanded = true) }
        }
    }

    companion object {
        val state = CurrenciesViewModel.State(
            currencies = emptyList(),
            loading = false,
            currentCurrency = "",
            rates = emptyMap(),
            isVisibleDialog = false,
            listFavorites = emptyList(),
            currentFilterType = FilterType.AZ,
            isMenuExpanded = false,
            textMenu = null
        )

        const val currency = "USD"
        val pairA1: Pair<String, Float> = Pair("a", 1f)
        val pairB2: Pair<String, Float> = Pair("b", 2f)
        val pairC3: Pair<String, Float> = Pair("c", 3f)
    }
}