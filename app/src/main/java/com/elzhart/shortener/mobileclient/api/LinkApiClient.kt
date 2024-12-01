package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.api.dto.ShortLinkDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LinkApiClient {

    private companion object {
        const val MOCK_RESPONSE_HOST = "http://elzhart.com"
    }

    suspend fun shortenUrl(input: LinkShortenInput): String {
        val response: Response<ShortLinkDto> = LinkApi.retrofitService.shortenUrl(input)
        var shortUrl: String? = null
        if (response.isSuccessful && response.body() != null) {
            shortUrl = response.body()?.url
        }

        return shortUrl.orEmpty()
    }

    fun mockShortenUrl(input: LinkShortenInput): String = "$MOCK_RESPONSE_HOST/${input.alias}"

}

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.130:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object LinkApi {
    val retrofitService: LinkApiService by lazy {
        retrofit.create(LinkApiService::class.java)
    }
}