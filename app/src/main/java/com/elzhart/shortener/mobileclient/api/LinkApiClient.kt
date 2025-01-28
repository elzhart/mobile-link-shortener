package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.api.dto.LoginInput
import com.elzhart.shortener.mobileclient.api.dto.ShortLinkDto
import com.elzhart.shortener.mobileclient.api.dto.SignupInput
import com.elzhart.shortener.mobileclient.api.dto.UserDto
import com.elzhart.shortener.mobileclient.api.dto.UserResponseDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LinkApiClient {

    companion object {
        const val MOCK_RESPONSE_HOST = "http://elzhart.com"
        const val AUTHORIZATION_HEADER = "AUTHORIZATION"
        const val AUTHORIZATION_PREFIX = "Bearer "
    }

    suspend fun shortenUrl(input: LinkShortenInput, token: String): String {
        val response: Response<ShortLinkDto> = LinkApi.retrofitService.shortenUrl(
            authHeader = AUTHORIZATION_PREFIX + token,
            requestBody = input
        )
        var shortUrl: String? = null
        if (response.isSuccessful && response.body() != null) {
            shortUrl = response.body()?.url
        }

        return shortUrl.orEmpty()
    }

    suspend fun login(input: LoginInput): UserResponseDto {
        val response: Response<UserDto> = LinkApi.retrofitService.login(input)
        val token: String = response.headers()[AUTHORIZATION_HEADER].orEmpty()
        return if (response.isSuccessful && token.isNotBlank()) {
            UserResponseDto(token = token, user = response.body())
        } else if (response.code() == 401) {
            UserResponseDto(errorMsg = "Bad credentials")
        } else {
            UserResponseDto(errorMsg = "Server is unavailable")
        }
    }

    suspend fun signup(input: SignupInput): UserResponseDto {
        val response: Response<UserDto> = LinkApi.retrofitService.signup(input)
        return if (response.isSuccessful) {
            UserResponseDto(user = response.body())
        } else {
            UserResponseDto(errorMsg = "${response.errorBody()}")
        }
    }

    fun mockShortenUrl(input: LinkShortenInput): String = "$MOCK_RESPONSE_HOST/${input.alias}"

}

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.128:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object LinkApi {
    val retrofitService: LinkApiService by lazy {
        retrofit.create(LinkApiService::class.java)
    }
}