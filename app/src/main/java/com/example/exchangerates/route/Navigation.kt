package com.example.exchangerates.route

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.exchangerates.presentation.currencies.CurrenciesScreen
import com.example.exchangerates.presentation.favorites.FavoritesScreen
import com.example.exchangerates.presentation.filters.FiltersScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    Column {
        Row(modifier = Modifier.weight(1f)) {
            NavHost(navController = navController, startDestination = NavigationRoute.ROUTE_CURRENCIES) {
                composable(NavigationRoute.ROUTE_CURRENCIES) {
                    CurrenciesScreen(
                        navigateToFilters = {
                            navController.navigate(NavigationRoute.ROUTE_FILTERS)
                        }
                    )
                }

                composable(NavigationRoute.ROUTE_FAVORITE) {
                    FavoritesScreen()
                }

                composable(NavigationRoute.ROUTE_FILTERS) {
                    FiltersScreen(
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
        when (currentBackStackEntry.value?.destination?.route) {
            NavigationRoute.ROUTE_FAVORITE, NavigationRoute.ROUTE_CURRENCIES -> BottomNavigation(
                navigateToCurrencies = {
                    navController.navigate(NavigationRoute.ROUTE_CURRENCIES)
                },
                navigateToFavorites = {
                    navController.navigate(NavigationRoute.ROUTE_FAVORITE)
                }
            )
            else -> {
                //nothing
            }
        }
    }
}