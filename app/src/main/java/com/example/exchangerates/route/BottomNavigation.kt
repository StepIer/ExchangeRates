package com.example.exchangerates.route

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.exchangerates.R
import com.example.exchangerates.route.model.BottomNavigationItem

@Composable
fun BottomNavigation(
    navigateToCurrencies: () -> Unit,
    navigateToFavorites: () -> Unit,
) {

    val selectedItem = remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            label = stringResource(R.string.currencies),
            painter = painterResource(id = R.drawable.currencies)
        ),
        BottomNavigationItem(
            label = stringResource(R.string.favorites),
            painter = painterResource(id = R.drawable.favorites_blue)
        ),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.painter, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                    when(index) {
                        0 -> navigateToCurrencies()
                        1 -> navigateToFavorites()
                        else -> {
                            //nothing
                        }
                    }
                }
            )
        }
    }
}