package com.elzhart.shortener.mobileclient.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextHeader(
    leadingIcon: Int, label: Int, modifier: Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.padding(start = 4.dp),
            painter = painterResource(id = leadingIcon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier
        )
    }
}