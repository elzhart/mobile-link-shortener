package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.api.dto.ShortLinkDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LinkApiService {

    @POST("/api/public/link/shorten")
    suspend fun shortenUrl(@Body input: LinkShortenInput): Response<ShortLinkDto>
}
