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
import com.example.compose.LinkShortenerTheme

enum class LinkShortenerScreen(val title: Int) {
    Login(title = R.string.log_in),
    Register(title = R.string.register),
    Shortener(title = R.string.shortener),
    Result(title = R.string.result)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LinkShortenerApp(viewModel: LinkViewModel = viewModel()) {

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LinkShortenerScreen.valueOf(
        backStackEntry?.destination?.route ?: LinkShortenerScreen.Shortener.name
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
        val uiState by viewModel.linkUiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = LinkShortenerScreen.Shortener.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkShortenerScreen.Shortener.name) {
                LinkScreen(
                    modifier = Modifier.padding(innerPadding),
                    linkUiState = uiState,
                    textReadOnly = false,
                    buttonTitle = R.string.shorten_title,
                    onLongLinkValueChange = viewModel::onLongLinkChange,
                    onAliasValueChange = viewModel::onAliasChange,
                    onButtonClick = {
                        viewModel.shorten()
                        navController.navigate(LinkShortenerScreen.Result.name)
                    }
                )
            }
            composable(route = LinkShortenerScreen.Result.name) {
                LinkScreen(
                    modifier = Modifier.padding(innerPadding),
                    linkUiState = uiState,
                    textReadOnly = true,
                    buttonTitle = R.string.shorten_another_title,
                    onButtonClick = {
                        clearAndBackToStart(viewModel, navController)
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