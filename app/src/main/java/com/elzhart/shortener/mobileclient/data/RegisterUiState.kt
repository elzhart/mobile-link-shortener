package com.elzhart.shortener.mobileclient.data

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val rePassword: String = ""
)
