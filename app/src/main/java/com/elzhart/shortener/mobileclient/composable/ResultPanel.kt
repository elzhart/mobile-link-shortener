package com.elzhart.shortener.mobileclient.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elzhart.shortener.mobileclient.R

@Composable
fun ResultPanel(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column {
        TextHeader(leadingIcon, label, modifier)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.size(4.dp))
            IconButton(
                onClick = {}, modifier = modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.content_copy),
                    contentDescription = null
                )
            }

        }
    }
}