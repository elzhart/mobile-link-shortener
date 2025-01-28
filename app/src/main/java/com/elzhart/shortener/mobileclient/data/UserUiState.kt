package com.elzhart.shortener.mobileclient.data

data class UserUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val rePassword: String = "",
    val errorMsg: String = "",
) {
    fun canLogin() = email.isNotBlank() && password.isNotBlank()

    fun canSignUp() = email.isNotBlank()
            && fullName.isNotBlank()
            && password.isNotBlank()
            && password == rePassword
}
