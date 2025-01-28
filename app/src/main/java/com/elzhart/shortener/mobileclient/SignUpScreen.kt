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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elzhart.shortener.mobileclient.composable.CommonButton
import com.elzhart.shortener.mobileclient.composable.TextEnterPanel
import com.elzhart.shortener.mobileclient.data.UserUiState
import com.example.compose.LinkShortenerTheme


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier.fillMaxHeight(),
    userUiState: UserUiState,
    errorMsg: String = "",
    onEmailValueChange: (String) -> Unit = {},
    onFullNameValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    onConfirmPasswordValueChange: (String) -> Unit = {},
    onSignUpButtonClick: () -> Unit
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
            label = R.string.email_short_label,
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
            label = R.string.full_name_label,
            hint = R.string.full_name_hint,
            leadingIcon = R.drawable.account,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next
            ),
            onValueChange = onFullNameValueChange,
            value = userUiState.fullName
        )

        Spacer(modifier = Modifier.size(32.dp))

        TextEnterPanel(
            label = R.string.password_short,
            hint = R.string.password_hint,
            leadingIcon = R.drawable.shield_account,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next
            ),
            onValueChange = onPasswordValueChange,
            value = userUiState.password
        )

        Spacer(modifier = Modifier.size(32.dp))

        TextEnterPanel(
            label = R.string.confirm_password,
            hint = R.string.confirm_password_hint,
            leadingIcon = R.drawable.shield_check,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
            ),
            onValueChange = onConfirmPasswordValueChange,
            value = userUiState.rePassword
        )

        Spacer(modifier = Modifier.size(32.dp))

        CommonButton(
            buttonTitle = R.string.signup_button_title,
            onButtonClick = onSignUpButtonClick,
            isButtonEnabled = userUiState.canSignUp()
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
fun SignupPreview() {
    LinkShortenerTheme {
        SignUpScreen(
            userUiState = UserUiState(
                email = "amelzhanov@gmail.com",
                fullName = "Elzhanov Artur Muratovich",
                password = "abc",
                rePassword = "abc"
            ),
            errorMsg = "Wrong email format!",
            onSignUpButtonClick = {}
        )
    }
}