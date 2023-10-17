package com.example.exchangerates.presentation.currencies

import androidx.lifecycle.ViewModel
import com.example.exchangerates.domain.currencies.GetFavoritesByFirstCurrencyUseCase
import com.example.exchangerates.domain.currencies.LoadCurrenciesUseCase
import com.example.exchangerates.domain.currencies.LoadRatesByCurrencyUseCase
import com.example.exchangerates.domain.currencies.SaveFavoriteUseCase
import com.example.exchangerates.domain.favorites.DeleteFavoritePairUseCase
import com.example.exchangerates.presentation.currencies.model.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val loadCurrenciesUseCase: LoadCurrenciesUseCase,
    private val loadRatesByCurrencyUseCase: LoadRatesByCurrencyUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoritesByFirstCurrencyUseCase: GetFavoritesByFirstCurrencyUseCase,
    private val deleteFavoritePairUseCase: DeleteFavoritePairUseCase
) : ViewModel(), ContainerHost<CurrenciesViewModel.State, CurrenciesViewModel.SideEffect> {

    override val container: Container<State, SideEffect> = container(State())

    init {
        intent {
            reduce { state.copy(loading = true) }
            loadCurrenciesUseCase.invoke()
                .onSuccess {
                    reduce { state.copy(currencies = it) }
                }.onFailure {
                    postSideEffect(SideEffect.ShowApiError)
                }
            reduce { state.copy(loading = false) }
        }
    }

    fun sendAction(action: Action) {
        when (action) {
            is Action.CurrencyChosen -> intent {
                reduce { state.copy(textMenu = action.currency, isMenuExpanded = false) }
                coroutineScope {
                    launch {
                        runCatching {
                            getFavoritesByFirstCurrencyUseCase.invoke(action.currency).collect() {
                                reduce { state.copy(listFavorites = it) }
                            }
                        }.onFailure {
                            postSideEffect(SideEffect.ShowDBError)
                        }
                    }
                    reduce { state.copy(loading = true, currentCurrency = action.currency) }
                    launch {
                        loadRatesByCurrencyUseCase.invoke(action.currency)
                            .onSuccess {
                                reduce { state.copy(rates = it) }
                            }.onFailure {
                                postSideEffect(SideEffect.ShowApiError)
                            }
                    }
                    reduce { state.copy(loading = false) }
                }
            }

            is Action.FavoriteImageClicked -> intent {
                if (state.listFavorites.contains(action.secondCurrency)) {
                    runCatching {
                        deleteFavoritePairUseCase.invoke(
                            Pair(
                                first = state.currentCurrency,
                                second = action.secondCurrency
                            )
                        )
                    }.onFailure {
                        postSideEffect(SideEffect.ShowDBError)
                    }
                } else {
                    runCatching {
                        saveFavoriteUseCase.invoke(
                            Pair(
                                first = state.currentCurrency,
                                second = action.secondCurrency
                            )
                        )
                    }.onFailure {
                        postSideEffect(SideEffect.ShowDBError)
                    }
                }
            }

            Action.FiltersButtonClicked -> intent {
                reduce { state.copy(isVisibleDialog = true) }
            }

            is Action.FilterDialogClosed -> intent {
                val rates = state.rates
                val sortedRates = when (action.filterType) {
                    FilterType.AZ -> {
                        rates.toSortedMap()
                    }

                    FilterType.ZA -> {
                        rates.toSortedMap(reverseOrder())
                    }

                    FilterType.ASC -> {
                        rates.entries
                            .sortedBy { it.value }
                            .associate { it.key to it.value }
                    }

                    FilterType.DESC -> {
                        rates.entries
                            .sortedByDescending { it.value } // Sort entries by values in descending order
                            .associate { it.key to it.value }
                    }

                    null -> {
                        rates
                    }
                }
                reduce { state.copy(rates = sortedRates, isVisibleDialog = false) }
            }

            Action.MenuDismissRequested -> intent {
                reduce { state.copy(isMenuExpanded = false) }
            }

            Action.MenuExpandedChanged -> intent {
                reduce { state.copy(isMenuExpanded = !state.isMenuExpanded) }
            }
        }
    }

    sealed class Action {
        data class CurrencyChosen(val currency: String) : Action()
        data class FavoriteImageClicked(val secondCurrency: String) : Action()
        object FiltersButtonClicked : Action()
        data class FilterDialogClosed(val filterType: FilterType?) : Action()
        object MenuExpandedChanged : Action()
        object MenuDismissRequested : Action()
    }

    data class State(
        val currencies: List<String> = emptyList(),
        val loading: Boolean = false,
        val currentCurrency: String = "",
        val rates: Map<String, Float> = emptyMap(),
        val isVisibleDialog: Boolean = false,
        val listFavorites: List<String> = emptyList(),
        val currentFilterType: FilterType = FilterType.AZ,
        val isMenuExpanded: Boolean = false,
        val textMenu: String? = null
    )

    sealed class SideEffect {
        object ShowApiError : SideEffect()
        object ShowDBError : SideEffect()
    }
}