package com.example.exchangerates.presentation.currencies.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.theme.AppColor

@Composable
fun FilterCard(
    text: String,
    isActivated: Boolean,
    onButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(AppColor.BG.default),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF343138),
            )
        )
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onButtonClicked() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = if (isActivated) {
                        R.drawable.radiobutton_on
                    } else {
                        R.drawable.radiobutton_off
                    }
                ),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewFilterCard() {
    FilterCard("Code A-Z", false) {}
}
