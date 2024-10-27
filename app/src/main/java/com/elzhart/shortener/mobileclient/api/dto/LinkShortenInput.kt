package com.elzhart.shortener.mobileclient.api.dto

data class LinkShortenInput(
    val longUrl: String,
    val alias: String = ""
)
