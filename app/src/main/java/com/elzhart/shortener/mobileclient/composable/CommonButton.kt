package com.elzhart.shortener.mobileclient.composable

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonButton(
    @StringRes buttonTitle: Int,
    onButtonClick: () -> Unit,
    isButtonEnabled: Boolean = false
) {
    Button(
        onClick = onButtonClick, modifier = Modifier, enabled = isButtonEnabled
    ) {
        Text(text = stringResource(id = buttonTitle))
    }
}