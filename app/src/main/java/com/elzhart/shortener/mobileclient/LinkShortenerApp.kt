package com.elzhart.shortener.mobileclient

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elzhart.shortener.mobileclient.ui.link.LinkViewModel
import com.elzhart.shortener.mobileclient.ui.login.LogInViewModel
import com.elzhart.shortener.mobileclient.ui.signup.SignUpViewModel
import com.example.compose.LinkShortenerTheme

enum class LinkShortenerScreen(val title: Int) {
    Login(title = R.string.log_in),
    SignUp(title = R.string.register),
    Shortener(title = R.string.shortener),
    Result(title = R.string.result)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LinkShortenerApp(
    loginViewModel: LogInViewModel = viewModel(factory = LogInViewModel.Factory),
    signupViewModel: SignUpViewModel = viewModel(),
    linkViewModel: LinkViewModel = viewModel(factory = LinkViewModel.Factory)
) {

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LinkShortenerScreen.valueOf(
        backStackEntry?.destination?.route ?: LinkShortenerScreen.Login.name
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LinkShortenerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val loginUiState by loginViewModel.loginUiState.collectAsState()
        val signUpUiState by signupViewModel.signupUiState.collectAsState()
        val linkUiState by linkViewModel.linkUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = LinkShortenerScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkShortenerScreen.Login.name) {
                LogInScreen(
                    modifier = Modifier.padding(innerPadding),
                    loginUiState = loginUiState,
                    errorMsg = loginUiState.errorMsg,
                    onEmailValueChange = loginViewModel::onEmailChange,
                    onPasswordValueChange = loginViewModel::onPasswordChange,
                    onLogInButtonClick = {
                        loginViewModel.login()
                        if (loginUiState.errorMsg.isBlank()) {
                            loginViewModel.clear()
                            navController.navigate(LinkShortenerScreen.Shortener.name)
                        } else {
                            navController.navigate(LinkShortenerScreen.Login.name)
                        }
                    },
                    onSignUpButtonClick = {
                        navController.navigate(LinkShortenerScreen.SignUp.name)
                    }
                )
            }
            composable(route = LinkShortenerScreen.SignUp.name) {
                SignUpScreen(
                    modifier = Modifier.padding(innerPadding),
                    signupUiState = signUpUiState,
                    errorMsg = signUpUiState.errorMsg,
                    onEmailValueChange = signupViewModel::onEmailChange,
                    onFullNameValueChange = signupViewModel::onFullNameChange,
                    onPasswordValueChange = signupViewModel::onPasswordChange,
                    onConfirmPasswordValueChange = signupViewModel::onPasswordConfirmChange,
                    onSignUpButtonClick = {
                        signupViewModel.signup()
                        if (signUpUiState.errorMsg.isBlank()) {
                            loginViewModel.onSignUp(signUpUiState)
                            signupViewModel.clear()
                            navController.navigate(LinkShortenerScreen.Login.name)
                        } else {
                            navController.navigate(LinkShortenerScreen.SignUp.name)
                        }
                    }
                )
            }
            composable(route = LinkShortenerScreen.Shortener.name) {
                LinkScreen(
                    modifier = Modifier.padding(innerPadding),
                    linkUiState = linkUiState,
                    textReadOnly = false,
                    buttonTitle = R.string.shorten_title,
                    onLongLinkValueChange = linkViewModel::onLongLinkChange,
                    onAliasValueChange = linkViewModel::onAliasChange,
                    onButtonClick = {
                        linkViewModel.shorten()
                        navController.navigate(LinkShortenerScreen.Result.name)
                    }
                )
            }
            composable(route = LinkShortenerScreen.Result.name) {
                LinkScreen(
                    modifier = Modifier.padding(innerPadding),
                    linkUiState = linkUiState,
                    textReadOnly = true,
                    buttonTitle = R.string.shorten_another_title,
                    onButtonClick = {
                        clearAndBackToStart(linkViewModel, navController)
                    })
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkShortenerAppBar(
    currentScreen: LinkShortenerScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.title),
                style = MaterialTheme.typography.displayLarge,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LinkShortenerPreview() {
    LinkShortenerTheme {
        LinkShortenerApp()
    }
}

private fun clearAndBackToStart(
    viewModel: LinkViewModel,
    navController: NavHostController
) {
    viewModel.clear()
    navController.popBackStack(LinkShortenerScreen.Shortener.name, inclusive = false)
}