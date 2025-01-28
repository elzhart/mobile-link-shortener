package com.elzhart.shortener.mobileclient.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.elzhart.shortener.mobileclient.LinkShortenerApplication
import com.elzhart.shortener.mobileclient.LinkShortenerScreen
import com.elzhart.shortener.mobileclient.api.LinkClientFactory
import com.elzhart.shortener.mobileclient.api.dto.LoginInput
import com.elzhart.shortener.mobileclient.api.dto.SignupInput
import com.elzhart.shortener.mobileclient.data.UserPreferencesRepository
import com.elzhart.shortener.mobileclient.data.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    fun onEmailChange(email: String) {
        _userUiState.update { userUiState ->
            userUiState.copy(
                email = email
            )
        }
    }

    fun onFullNameChange(fullName: String) {
        _userUiState.update { userUiState ->
            userUiState.copy(
                fullName = fullName
            )
        }
    }

    fun onPasswordChange(password: String) {
        _userUiState.update { userUiState ->
            userUiState.copy(
                password = password
            )
        }
    }

    fun onPasswordConfirmChange(rePassword: String) {
        _userUiState.update { userUiState ->
            userUiState.copy(
                rePassword = rePassword
            )
        }
    }

    fun onLogin(navController: NavHostController) {
        viewModelScope.launch {
            val errorMsg = loginRequest()
            _userUiState.update { loginUiState ->
                loginUiState.copy(
                    errorMsg = errorMsg
                )
            }
            if (errorMsg.isBlank()) {
                navController.popBackStack()
                navController.navigate(LinkShortenerScreen.Shortener.name)
            } else {
                navController.navigate(LinkShortenerScreen.Login.name)
            }
        }
    }

    fun onSignUp(navController: NavHostController) {
        viewModelScope.launch {
            val errorMsg = signUpRequest()
            _userUiState.update { loginUiState ->
                loginUiState.copy(
                    errorMsg = errorMsg
                )
            }
            if (errorMsg.isBlank()) {
                navController.popBackStack()
                navController.navigate(LinkShortenerScreen.Login.name)
            } else {
                navController.popBackStack()
                navController.navigate(LinkShortenerScreen.SignUp.name)
            }
        }
    }

    fun clear() {
        _userUiState.value = UserUiState()
    }

    private suspend fun loginRequest(): String {
        val (email, _, password, _, _) = _userUiState.value
        val client = LinkClientFactory.getInstance()
        val resp = client.login(
            LoginInput(username = email, password = password)
        )
        if (resp.token.isNotEmpty()) {
            userPreferencesRepository.saveToken(resp.token)
        }
        return resp.errorMsg
    }

    private suspend fun signUpRequest(): String {
        val (email, fullName, password, rePassword, _) = _userUiState.value
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LinkShortenerApplication)
                UserViewModel(application.userPreferencesRepository)
            }
        }
    }
}