package com.elzhart.shortener.mobileclient.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.elzhart.shortener.mobileclient.LinkReleaseApplication
import com.elzhart.shortener.mobileclient.api.LinkClientFactory
import com.elzhart.shortener.mobileclient.api.dto.LoginInput
import com.elzhart.shortener.mobileclient.data.LoginUiState
import com.elzhart.shortener.mobileclient.data.SignUpUiState
import com.elzhart.shortener.mobileclient.data.UserPreferencesRepository
import com.elzhart.shortener.mobileclient.ui.link.LinkViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    fun onEmailChange(email: String) {
        _loginUiState.update { loginUiState ->
            loginUiState.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update { loginUiState ->
            loginUiState.copy(
                password = password
            )
        }
    }

    fun onSignUp(signUpUiState: SignUpUiState) {
        _loginUiState.update { loginUiState ->
            loginUiState.copy(
                email = signUpUiState.email,
                password = signUpUiState.password
            )
        }
    }


    fun login() {
        viewModelScope.launch {
            _loginUiState.update { loginUiState ->
                loginUiState.copy(
                    errorMsg = loginRequest()
                )
            }
        }
    }

    fun clear() {
        _loginUiState.value = LoginUiState()
    }

    private suspend fun loginRequest(): String {
        val (email, password, _) = _loginUiState.value
        val client = LinkClientFactory.getInstance()
        val resp = client.login(
            LoginInput(username = email, password = password)
        )
        if (resp.token.isNotEmpty()) {
            userPreferencesRepository.saveToken(resp.token)
        }
        return resp.errorMsg
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