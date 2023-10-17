package com.example.exchangerates.presentation.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.components.AppRateCard
import com.example.exchangerates.presentation.ui.theme.AppColor
import com.example.exchangerates.presentation.ui.theme.AppTextStyles
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {

    val viewModel: FavoritesViewModel = hiltViewModel()
    val screenState = viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            FavoritesViewModel.SideEffect.ShowApiError,
            FavoritesViewModel.SideEffect.ShowDBError -> {
                Toast.makeText(
                    context,
                    context.getText(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColor.BG.header,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.favorites),
                        style = AppTextStyles.topAppBarTitle
                    )
                }
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screenState.value.currencyPairs) { item ->
                    Column {
                        AppRateCard(
                            leftText = "${item.firstCurrency}/${item.secondCurrency}",
                            rightText = item.rate?.toString() ?: "loading...",
                            isFavorite = true,
                            onFavoriteImageClick = {
                                viewModel.sendAction(
                                    FavoritesViewModel.Action.FavoriteImageClicked(
                                        item
                                    )
                                )
                            })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}