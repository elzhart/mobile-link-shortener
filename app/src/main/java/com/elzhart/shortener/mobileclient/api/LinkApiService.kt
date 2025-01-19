package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.LinkApiClient.Companion.AUTHORIZATION_HEADER
import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.api.dto.LoginInput
import com.elzhart.shortener.mobileclient.api.dto.ShortLinkDto
import com.elzhart.shortener.mobileclient.api.dto.SignupInput
import com.elzhart.shortener.mobileclient.api.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LinkApiService {

    @POST("/api/public/login")
    suspend fun login(@Body input: LoginInput): Response<UserDto>

    @POST("/api/public/register")
    suspend fun signup(@Body input: SignupInput): Response<UserDto>

    @POST("/api/link/shorten")
    suspend fun shortenUrl(
        @Header(AUTHORIZATION_HEADER) authHeader: String,
        @Body requestBody: LinkShortenInput
    ): Response<ShortLinkDto>
}
