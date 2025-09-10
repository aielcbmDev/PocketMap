package charly.baquero.pocketmap.ui.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import charly.baquero.pocketmap.ui.startup.ErrorContent
import charly.baquero.pocketmap.ui.startup.LoadingContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DisplayGroupsScreen() {
    val viewModel = koinViewModel<DisplayGroupsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (val currentState = state) {
                is DisplayGroupsViewState.Loading -> {
                    LoadingContent()
                }

                is DisplayGroupsViewState.Success -> {
                }

                is DisplayGroupsViewState.Error -> {
                    ErrorContent(
                        { viewModel.displayAllGroups() }
                    )
                }
            }
        }
    }
}
