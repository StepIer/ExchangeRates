package com.example.exchangerates.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.theme.AppColor

@Composable
fun AppRateCard(
    leftText: String,
    rightText: String,
    isFavorite: Boolean,
    onFavoriteImageClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppColor.BG.card,
        ),
        modifier = Modifier.height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = leftText,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(500),
                    color = AppColor.Main.textDefault,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = rightText,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = AppColor.Main.textDefault,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(
                    id = if (isFavorite) {
                        R.drawable.favorites_on
                    } else {
                        R.drawable.favorites_off
                    }
                ),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onFavoriteImageClick()
                }
            )
        }
    }
}

@Preview
@Composable
fun AppRateCardPreview() {
    AppRateCard(
        leftText = "ASD",
        rightText = "3.020202",
        isFavorite = true,
        onFavoriteImageClick = {}
    )
}
