package com.example.exchangerates.presentation.favorites

import androidx.lifecycle.ViewModel
import com.example.exchangerates.domain.favorites.DeleteFavoritePairUseCase
import com.example.exchangerates.domain.favorites.GetFavoriteUseCase
import com.example.exchangerates.domain.favorites.LoadRatesByCurrenciesUseCase
import com.example.exchangerates.presentation.favorites.model.CurrencyRate
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val loadRatesByCurrenciesUseCase: LoadRatesByCurrenciesUseCase,
    private val deleteFavoritePairUseCase: DeleteFavoritePairUseCase
) : ViewModel(),
    ContainerHost<FavoritesViewModel.State, FavoritesViewModel.SideEffect> {

    override val container: Container<State, SideEffect> = container(State())

    init {
        intent {
            runCatching {
                getFavoriteUseCase.invoke().collect {
                    val listCurrencyPairs =
                        it.map { currencyPair ->
                            CurrencyRate(
                                currencyPair.first,
                                currencyPair.second,
                                null
                            )
                        }
                    reduce { state.copy(currencyPairs = listCurrencyPairs) }
                    state.currencyPairs.forEach { item ->
                        loadRatesByCurrenciesUseCase.invoke(
                            Pair(
                                item.firstCurrency,
                                item.secondCurrency
                            )
                        ).onSuccess {
                            reduce {
                                state.copy(currencyPairs = state.currencyPairs.map { currencyRate ->
                                    if (currencyRate.firstCurrency == item.firstCurrency
                                        && currencyRate.secondCurrency == item.secondCurrency
                                    ) {
                                        CurrencyRate(
                                            currencyRate.firstCurrency,
                                            currencyRate.secondCurrency,
                                            rate = it
                                        )
                                    } else {
                                        currencyRate
                                    }
                                })
                            }
                        }.onFailure {
                            postSideEffect(SideEffect.ShowDBError)
                        }
                    }
                }
            }.onFailure {
                postSideEffect(SideEffect.ShowDBError)
            }
        }
    }

    fun sendAction(action: Action) {
        when (action) {
            is Action.FavoriteImageClicked -> intent {
                runCatching {
                    deleteFavoritePairUseCase.invoke(
                        Pair(
                            action.currencyRate.firstCurrency,
                            action.currencyRate.secondCurrency
                        )
                    )
                }.onFailure {
                    postSideEffect(SideEffect.ShowDBError)
                }
            }
        }
    }

    sealed class Action {
        data class FavoriteImageClicked(val currencyRate: CurrencyRate) : Action()
    }

    sealed class SideEffect {
        object ShowApiError : SideEffect()
        object ShowDBError : SideEffect()
    }

    data class State(
        val currencyPairs: List<CurrencyRate> = emptyList()
    )
}