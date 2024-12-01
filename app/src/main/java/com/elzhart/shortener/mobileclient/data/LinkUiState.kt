package com.elzhart.shortener.mobileclient.data

data class LinkUiState(
    val shortLink: String = "",
    val longLink: String = "",
    var alias: String = ""
)
