package com.elzhart.shortener.mobileclient.data

data class SignUpUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val rePassword: String = "",
    val errorMsg: String =""
) {
    fun isFullFilled() = email.isNotBlank()
            && fullName.isNotBlank()
            && password.isNotBlank()
            && password == rePassword
}
