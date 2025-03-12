package com.rocketseat.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import com.rocketseat.rocketia.ui.event.WelcomeUiEvent
import com.rocketseat.rocketia.ui.state.WelcomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val checkHasSelectedStackUseCase: CheckHasSelectedStackUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<WelcomeUiState> = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: WelcomeUiEvent) {
        when(event) {
            WelcomeUiEvent.CheckHasSelectedStack -> checkHasSelectedStack()
        }
    }

    private fun checkHasSelectedStack() {
        viewModelScope.launch {
            val hasSelectedStack = checkHasSelectedStackUseCase.invoke()
            _uiState.update { currentUiState ->
                currentUiState.copy(hasSelectedStack = hasSelectedStack)
            }
        }
    }

}