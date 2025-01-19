package com.elzhart.shortener.mobileclient.ui.link

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.elzhart.shortener.mobileclient.LinkReleaseApplication
import com.elzhart.shortener.mobileclient.api.LinkClientFactory
import com.elzhart.shortener.mobileclient.api.dto.LinkShortenInput
import com.elzhart.shortener.mobileclient.data.LinkUiState
import com.elzhart.shortener.mobileclient.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LinkViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

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
                    shortLink = shortenLink()
                )
            }
        }
    }

    fun clear() {
        _linkUiState.value = LinkUiState()
    }

    private suspend fun shortenLink(): String {
        val (_, link, alias) = _linkUiState.value
        val token = userPreferencesRepository.getToken.first()
        if (token.isBlank() || link.length < 10) return ""
        val client = LinkClientFactory.getInstance()
        return client.shortenUrl(
            input = LinkShortenInput(longUrl = link, alias = alias),
            token = token
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LinkReleaseApplication)
                LinkViewModel(application.userPreferencesRepository)
            }
        }
    }
}