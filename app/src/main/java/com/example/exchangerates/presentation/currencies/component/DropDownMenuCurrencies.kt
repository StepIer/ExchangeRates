package com.example.exchangerates.presentation.currencies.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuCurrencies(
    modifier: Modifier,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    text: String,
    onDismissRequest: () -> Unit,
    items: List<String>,
    onItemClick: (selectionCurrency: String) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { onExpandedChange() },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .fillMaxWidth()
                .height(52.dp),
            value = text,
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(size = 8.dp),
            trailingIcon = {

                Image(
                    painter = painterResource(
                        id = if (expanded) {
                            R.drawable.arrow_up
                        } else {
                            R.drawable.arrow_down
                        }
                    ), contentDescription = null
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = AppColor.Main.textDefault
            ),

            )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() },
        ) {

            // The default case experiences performance issues when we have 171 list items.
            // I suggest initiating a conversation with the designer, project manager,
            // or business analyst to address this concern. Default case - Column items
            Box(modifier = Modifier.size(500.dp, 300.dp)) {
                LazyColumn {
                    items(items) { selectionCurrency ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = {
                                Text(text = selectionCurrency)
                            },
                            onClick = {
                                onItemClick(selectionCurrency)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}