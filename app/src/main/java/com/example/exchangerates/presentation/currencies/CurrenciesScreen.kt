package com.example.exchangerates.presentation.currencies

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangerates.R
import com.example.exchangerates.presentation.currencies.component.DropDownMenuCurrencies
import com.example.exchangerates.presentation.ui.components.AppRateCard
import com.example.exchangerates.presentation.ui.components.LoadingComponent
import com.example.exchangerates.presentation.ui.theme.AppColor
import com.example.exchangerates.presentation.ui.theme.AppTextStyles
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen() {
    val viewModel: CurrenciesViewModel = hiltViewModel()
    val screenState = viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            CurrenciesViewModel.SideEffect.ShowApiError, CurrenciesViewModel.SideEffect.ShowDBError -> {
                Toast.makeText(
                    context, context.getText(R.string.something_went_wrong), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                Modifier.background(AppColor.BG.header)
            ) {
                TopAppBar(colors = topAppBarColors(
                    containerColor = AppColor.BG.header,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ), title = {
                    Text(
                        text = stringResource(R.string.currencies),
                        style = AppTextStyles.topAppBarTitle
                    )
                })

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    DropDownMenuCurrencies(
                        modifier = Modifier.weight(1f),
                        expanded = screenState.value.isMenuExpanded,
                        onExpandedChange = { viewModel.sendAction(CurrenciesViewModel.Action.MenuExpandedChanged) },
                        text = screenState.value.textMenu
                            ?: stringResource(R.string.choose_currency),
                        onDismissRequest = { viewModel.sendAction(CurrenciesViewModel.Action.MenuDismissRequested) },
                        items = screenState.value.currencies,
                        onItemClick = { selectionCurrency ->
                            viewModel.sendAction(
                                CurrenciesViewModel.Action.CurrencyChosen(
                                    selectionCurrency
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF9DACDC),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .size(52.dp)
                            .background(
                                color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp)
                            )
                            .clickable { viewModel.sendAction(CurrenciesViewModel.Action.FiltersButtonClicked) },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "image description",
                            contentScale = ContentScale.None
                        )
                    }
                }
            }
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screenState.value.rates.keys.toList()) { item ->

                    AppRateCard(
                        leftText = item,
                        rightText = screenState.value.rates[item].toString(),
                        isFavorite = screenState.value.listFavorites.contains(item),
                        onFavoriteImageClick = {
                            viewModel.sendAction(
                                CurrenciesViewModel.Action.FavoriteImageClicked(
                                    item
                                )
                            )
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (screenState.value.isVisibleDialog) {
                FiltersDialog(dismissDialog = { filterType ->
                    viewModel.sendAction(CurrenciesViewModel.Action.FilterDialogClosed(filterType))
                })
            }
            if (screenState.value.loading) {
                LoadingComponent()
            }
        }
    }
}