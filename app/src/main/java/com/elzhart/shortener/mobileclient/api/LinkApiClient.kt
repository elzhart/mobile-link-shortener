package com.elzhart.shortener.mobileclient.api

import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LinkApiClient {

    private companion object {
        const val MOCK_RESPONSE_HOST = "http://elzhart.com"
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://elzhart.shortener.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: LinkApiService = retrofit.create(LinkApiService::class.java)

    fun shortenUrl(input: LinkShortenInput): String? {
        val call: Call<String> = apiService.shortenUrl(input)
        var shortUrl: String? = null
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String?>) {
                if (response.isSuccessful && response.body() != null) {
                    shortUrl = response.body()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                throw t
            }
        })

        return shortUrl
    }

    fun mockShortenUrl(input: LinkShortenInput): String = "$MOCK_RESPONSE_HOST/${input.alias}"

}