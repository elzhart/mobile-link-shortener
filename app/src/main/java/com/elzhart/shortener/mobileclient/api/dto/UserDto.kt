package com.elzhart.shortener.mobileclient.api.dto


data class UserResponseDto(
    val token: String = "",
    val user: UserDto? = null,
    val errorMsg: String = ""
)

data class UserDto(
    val id: String,
    val name: String,
    val fullName: String
)
