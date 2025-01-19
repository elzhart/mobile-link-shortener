package com.elzhart.shortener.mobileclient.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elzhart.shortener.mobileclient.api.LinkClientFactory
import com.elzhart.shortener.mobileclient.api.dto.SignupInput
import com.elzhart.shortener.mobileclient.data.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signupUiState: StateFlow<SignUpUiState> = _signUpUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _signUpUiState.update { signUpUiState ->
            signUpUiState.copy(
                email = email
            )
        }
    }

    fun onFullNameChange(fullName: String) {
        _signUpUiState.update { signUpUiState ->
            signUpUiState.copy(
                fullName = fullName
            )
        }
    }

    fun onPasswordChange(password: String) {
        _signUpUiState.update { signUpUiState ->
            signUpUiState.copy(
                password = password
            )
        }
    }

    fun onPasswordConfirmChange(rePassword: String) {
        _signUpUiState.update { signUpUiState ->
            signUpUiState.copy(
                rePassword = rePassword
            )
        }
    }

    fun signup() {
        viewModelScope.launch {
            _signUpUiState.update { signUpUiState ->
                signUpUiState.copy(
                    errorMsg = signUpRequest()
                )
            }
        }
    }

    fun clear() {
        _signUpUiState.value = SignUpUiState()
    }

    private suspend fun signUpRequest(): String {
        val (email, fullName, password, rePassword, _) = _signUpUiState.value
        val client = LinkClientFactory.getInstance()
        val resp = client.signup(
            SignupInput(
                name = email,
                fullName = fullName,
                password = password,
                rePassword = rePassword
            )
        )
        return resp.errorMsg
    }
}