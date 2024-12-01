package com.elzhart.shortener.mobileclient

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elzhart.shortener.mobileclient.data.LinkUiState
import com.example.compose.LinkShortenerTheme


@Composable
fun LinkScreen(
    modifier: Modifier = Modifier.fillMaxHeight(),
    linkUiState: LinkUiState,
    textReadOnly: Boolean = false,
    @StringRes
    buttonTitle: Int,
    onLongLinkValueChange: (String) -> Unit = {},
    onAliasValueChange: (String) -> Unit = {},
    onButtonClick: () -> Unit
) {

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextEnterPanel(
            label = R.string.long_url_label,
            hint = R.string.long_url_hint,
            leadingIcon = R.drawable.link,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Uri, imeAction = ImeAction.Next
            ),
            onValueChange = onLongLinkValueChange,
            value = linkUiState.longLink,
            readOnly = textReadOnly
        )

        Spacer(modifier = Modifier.size(32.dp))

        TextEnterPanel(
            label = R.string.alias_label,
            hint = R.string.alias_hint,
            leadingIcon = R.drawable.alias,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
            ),
            onValueChange = onAliasValueChange,
            value = linkUiState.alias,
            readOnly = textReadOnly
        )

        if (linkUiState.shortLink.isNotBlank()) {
            Spacer(modifier = Modifier.size(32.dp))

            ResultPanel(
                label = R.string.short_url_label,
                leadingIcon = R.drawable.done,
                value = linkUiState.shortLink
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        ShortenButton(
            buttonTitle = buttonTitle,
            onButtonClick = onButtonClick,
            isButtonEnabled = linkUiState.longLink.isNotBlank()
        )
    }
}

@Composable
fun ShortenButton(
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

@Composable
fun TextEnterPanel(
    @StringRes label: Int,
    @StringRes hint: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false
) {
    Column {
        TextHeader(leadingIcon, label, modifier)
        Spacer(modifier = Modifier.size(4.dp))
        TextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onValueChange,
            label = { Text(stringResource(hint), style = MaterialTheme.typography.labelSmall) },
            keyboardOptions = keyboardOptions,
            textStyle = MaterialTheme.typography.bodyLarge,
            readOnly = readOnly
        )
    }
}

@Composable
private fun TextHeader(
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


@Preview(showBackground = true)
@Composable
fun LinkLayoutPreview() {
    LinkShortenerTheme {
        LinkScreen(
            linkUiState = LinkUiState(
                longLink = "http://long_link_test.com",
                alias = "abc",
                shortLink = "http://test.com/abc"
            ),
            textReadOnly = true,
            buttonTitle = R.string.shorten_another_title,
            onButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LongLinkEnterPanelPreview() {
    LinkShortenerTheme {
        TextEnterPanel(
            label = R.string.long_url_label,
            hint = R.string.long_url_hint,
            leadingIcon = R.drawable.link,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            ),
            value = stringResource(id = R.string.mock_long_url)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AliasEnterPanelPreview() {
    LinkShortenerTheme {
        TextEnterPanel(
            label = R.string.alias_label,
            hint = R.string.alias_hint,
            leadingIcon = R.drawable.alias,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            value = stringResource(id = R.string.mock_alias)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShortLongResultPanelPreview() {
    LinkShortenerTheme {
        ResultPanel(
            label = R.string.short_url_label,
            leadingIcon = R.drawable.done,
            value = stringResource(id = R.string.mock_short_url)
        )
    }
}
