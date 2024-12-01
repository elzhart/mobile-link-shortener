package com.elzhart.shortener.mobileclient.ui.link

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzhart.shortener.mobileclient.api.LinkClientFactory
import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.data.LinkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LinkViewModel : ViewModel() {

    private val _linkUiState = MutableStateFlow(LinkUiState())
    val linkUiState: StateFlow<LinkUiState> = _linkUiState.asStateFlow()

    fun onLongLinkChange(longLink: String) {
        _linkUiState.update { linkUiState ->
            linkUiState.copy(
                longLink = longLink
            )
        }
    }

    fun onAliasChange(alias: String) {
        _linkUiState.update { linkUiState ->
            linkUiState.copy(
                alias = alias
            )
        }
    }

    fun shorten() {
        viewModelScope.launch {
            _linkUiState.update { linkUiState ->
                linkUiState.copy(
                    shortLink = shortenLink(linkUiState.longLink, linkUiState.alias)
                )
            }
        }
    }

    fun clear() {
        _linkUiState.value = LinkUiState()
    }

    private suspend fun shortenLink(linkInput: String, aliasInput: String): String {
        if (linkInput.length < 10) return ""
        val client = LinkClientFactory.getInstance()
        return client.shortenUrl(
            LinkShortenInput(longUrl = linkInput, alias = aliasInput)
        )
    }
}