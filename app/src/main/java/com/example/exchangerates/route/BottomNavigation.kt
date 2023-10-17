package com.example.exchangerates.route

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.theme.AppColor
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

    NavigationBar(
        containerColor = AppColor.Main.lightPrimary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Image(item.painter, contentDescription = item.label) },
                label = {
                    Text(
                        text = item.label,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(600),
                            color = AppColor.Main.textDefault,
                            textAlign = TextAlign.Center,
                        )
                    )
                },
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                    when (index) {
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