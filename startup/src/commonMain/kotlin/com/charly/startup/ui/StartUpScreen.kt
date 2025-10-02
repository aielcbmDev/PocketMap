package com.charly.startup.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pocketmap.startup.generated.resources.Res
import pocketmap.startup.generated.resources.retry
import pocketmap.startup.generated.resources.something_went_wrong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartUpScreen(
    onStartUpSuccess: () -> Unit
) {
    val viewModel = koinViewModel<StartUpViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (val currentState = state) {
                is StartUpViewState.Loading -> {
                    LoadingContent()
                }

                is StartUpViewState.Success -> {
                    onStartUpSuccess.invoke()
                }

                is StartUpViewState.Error -> {
                    ErrorContent(
                        { viewModel.onEvent(ViewEvent.PrePopulateDatabase) }
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.LoadingContent() {
    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
}

@Composable
fun BoxScope.ErrorContent(
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(Alignment.Center)
    ) {
        Text(
            text = stringResource(Res.string.something_went_wrong),
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(Res.string.retry))
        }
    }
}
