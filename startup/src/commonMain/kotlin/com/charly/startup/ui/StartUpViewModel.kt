package com.charly.startup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charly.domain.usecases.prepopulate.PrePopulateDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartUpViewModel(
    private val prePopulateDatabaseUseCase: PrePopulateDatabaseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StartUpViewState>(StartUpViewState.Loading)
    val state: StateFlow<StartUpViewState> = _state

    init {
        onEvent(ViewEvent.PrePopulateDatabase)
    }

    fun onEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.PrePopulateDatabase -> prePopulateDatabase()
        }
    }

    private fun prePopulateDatabase() {
        viewModelScope.launch {
            _state.value = StartUpViewState.Loading
            try {
                prePopulateDatabaseUseCase.execute()
                _state.value = StartUpViewState.Success
            } catch (_: Exception) {
                _state.value = StartUpViewState.Error
            }
        }
    }
}

sealed interface StartUpViewState {
    data object Loading : StartUpViewState
    data object Success : StartUpViewState
    data object Error : StartUpViewState
}

sealed interface ViewEvent {
    data object PrePopulateDatabase : ViewEvent
}
