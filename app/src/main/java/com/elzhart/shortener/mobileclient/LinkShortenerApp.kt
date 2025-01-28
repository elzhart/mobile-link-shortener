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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elzhart.shortener.mobileclient.ui.link.LinkViewModel
import com.elzhart.shortener.mobileclient.ui.login.UserViewModel
import com.example.compose.LinkShortenerTheme

enum class LinkShortenerScreen(val title: Int) {
    Login(title = R.string.log_in),
    SignUp(title = R.string.register),
    Shortener(title = R.string.shortener),
    Result(title = R.string.result);

    companion object {
        val canLogOutScreens: List<String> = listOf(Shortener.name, Result.name)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LinkShortenerApp(
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
    linkViewModel: LinkViewModel = viewModel(factory = LinkViewModel.Factory)
) {

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LinkShortenerScreen.valueOf(
        backStackEntry?.destination?.route ?: LinkShortenerScreen.Login.name
    )

    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LinkShortenerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                canLogOut = navController.currentDestination?.route in LinkShortenerScreen.canLogOutScreens,
                logOut = {
                    linkViewModel.clearWithPreferences()
                    userViewModel.clear()
                    navController.popBackStack()
                    navController.navigate(LinkShortenerScreen.Login.name)
                },
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val userUiState by userViewModel.userUiState.collectAsState()
        val linkUiState by linkViewModel.linkUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = LinkShortenerScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkShortenerScreen.Login.name) {
                LogInScreen(
                    modifier = Modifier.padding(innerPadding),
                    userUiState = userUiState,
                    errorMsg = userUiState.errorMsg,
                    onEmailValueChange = userViewModel::onEmailChange,
                    onPasswordValueChange = userViewModel::onPasswordChange,
                    onLogInButtonClick = { userViewModel.onLogin(navController) },
                    onSignUpButtonClick = {
                        navController.popBackStack()
                        navController.navigate(LinkShortenerScreen.SignUp.name)
                    }
                )
            }
            composable(route = LinkShortenerScreen.SignUp.name) {
                SignUpScreen(
                    modifier = Modifier.padding(innerPadding),
                    userUiState = userUiState,
                    errorMsg = userUiState.errorMsg,
                    onEmailValueChange = userViewModel::onEmailChange,
                    onFullNameValueChange = userViewModel::onFullNameChange,
                    onPasswordValueChange = userViewModel::onPasswordChange,
                    onConfirmPasswordValueChange = userViewModel::onPasswordConfirmChange,
                    onSignUpButtonClick = { userViewModel.onSignUp(navController) }
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
                        clearAndBackToStart(
                            linkViewModel::clear,
                            navController,
                            LinkShortenerScreen.Shortener.name
                        )
                    },
                    onCopyClick = {
                        clipboardManager.setText(AnnotatedString(linkUiState.shortLink))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkShortenerAppBar(
    currentScreen: LinkShortenerScreen,
    canLogOut: Boolean,
    canNavigateBack: Boolean,
    logOut: () -> Unit,
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

        actions = {
            if (canLogOut) {
                IconButton(onClick = logOut) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = stringResource(R.string.log_out)
                    )
                }
            }
        },
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
    viewModelClear: () -> Unit,
    navController: NavHostController,
    destination: String
) {
    viewModelClear.invoke()
    navController.popBackStack(destination, inclusive = false)
}