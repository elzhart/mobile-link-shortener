package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LinkApiService {

    @POST("/api/link/shorten")
    fun shortenUrl(@Body input: LinkShortenInput): Call<String>
}