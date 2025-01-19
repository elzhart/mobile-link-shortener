package com.elzhart.shortener.mobileclient.data

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMsg: String = "",
) {
    fun isFullFilled() = email.isNotBlank() && password.isNotBlank()
}
