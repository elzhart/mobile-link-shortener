package com.elzhart.shortener.mobileclient.api

class LinkClientFactory {

    companion object {
        private val client = LinkApiClient()

        @JvmStatic
        fun getInstance() = client
    }

}