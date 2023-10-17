package com.example.exchangerates.presentation.currencies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.exchangerates.R
import com.example.exchangerates.presentation.currencies.component.FilterCard
import com.example.exchangerates.presentation.currencies.model.FilterType
import com.example.exchangerates.presentation.ui.theme.AppColor
import com.example.exchangerates.presentation.ui.theme.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersDialog(
    dismissDialog: (filterType: FilterType?) -> Unit,
) {
    val currentFilterType = remember { mutableStateOf(FilterType.AZ) }

    Dialog(
        onDismissRequest = {
            dismissDialog(null)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppColor.BG.header,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.filters),
                            style = AppTextStyles.topAppBarTitle
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            dismissDialog(null)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = AppColor.Main.primary
                            )
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        modifier = Modifier
                            .height(20.dp),
                        text = stringResource(R.string.sort_by),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF767676),

                            )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FilterCard(
                        text = stringResource(R.string.code_a_z),
                        isActivated = currentFilterType.value == FilterType.AZ,
                        onButtonClicked = { currentFilterType.value = FilterType.AZ })
                    FilterCard(
                        text = stringResource(R.string.code_z_a),
                        isActivated = currentFilterType.value == FilterType.ZA,
                        onButtonClicked = { currentFilterType.value = FilterType.ZA })
                    FilterCard(
                        text = stringResource(R.string.quote_asc),
                        isActivated = currentFilterType.value == FilterType.ASC,
                        onButtonClicked = { currentFilterType.value = FilterType.ASC })
                    FilterCard(
                        text = stringResource(R.string.quote_desc),
                        isActivated = currentFilterType.value == FilterType.DESC,
                        onButtonClicked = { currentFilterType.value = FilterType.DESC })
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColor.Main.primary),
                        onClick = {
                            dismissDialog(currentFilterType.value)
                        }) {
                        Text(
                            text = stringResource(R.string.apply),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.1.sp,
                            )
                        )
                    }
                }
            }
        }
    }
}