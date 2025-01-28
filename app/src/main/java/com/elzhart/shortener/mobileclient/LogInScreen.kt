package com.elzhart.shortener.mobileclient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elzhart.shortener.mobileclient.composable.CommonButton
import com.elzhart.shortener.mobileclient.composable.TextEnterPanel
import com.elzhart.shortener.mobileclient.data.UserUiState
import com.example.compose.LinkShortenerTheme


@Composable
fun LogInScreen(
    modifier: Modifier = Modifier.fillMaxHeight(),
    userUiState: UserUiState,
    errorMsg: String = "",
    onEmailValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    onLogInButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextEnterPanel(
            label = R.string.email_label,
            hint = R.string.email_hint,
            leadingIcon = R.drawable.email,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Uri, imeAction = ImeAction.Next
            ),
            onValueChange = onEmailValueChange,
            value = userUiState.email
        )

        Spacer(modifier = Modifier.size(32.dp))

        TextEnterPanel(
            label = R.string.password_label,
            hint = R.string.password_hint,
            leadingIcon = R.drawable.shield_account,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
            ),
            onValueChange = onPasswordValueChange,
            value = userUiState.password
        )

        Spacer(modifier = Modifier.size(32.dp))

        CommonButton(
            buttonTitle = R.string.login_button_title,
            onButtonClick = onLogInButtonClick,
            isButtonEnabled = userUiState.canLogin()
        )
        Text(
            text = stringResource(id = R.string.account_exist_question),
            style = MaterialTheme.typography.labelSmall
        )
        CommonButton(
            buttonTitle = R.string.signup_button_title,
            onButtonClick = onSignUpButtonClick,
            isButtonEnabled = true
        )
        if (errorMsg.isNotBlank()) {
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LinkShortenerTheme {
        LogInScreen(
            userUiState = UserUiState(
                email = "amelzhanov@gmail.com",
                password = "abc"
            ),
            errorMsg = "Bad credentials!",
            onLogInButtonClick = {},
            onSignUpButtonClick = {}
        )
    }
}



