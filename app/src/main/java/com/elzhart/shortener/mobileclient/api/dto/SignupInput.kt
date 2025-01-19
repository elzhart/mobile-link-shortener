package com.elzhart.shortener.mobileclient.api.dto

data class SignupInput(
    val name: String,
    val fullName: String,
    val password: String,
    val rePassword: String
)
