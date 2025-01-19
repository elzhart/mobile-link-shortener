package com.elzhart.shortener.mobileclient.api.dto

data class LoginInput(
    val username: String,
    val password: String = ""
)
