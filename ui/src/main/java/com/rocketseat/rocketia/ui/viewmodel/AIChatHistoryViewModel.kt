package com.rocketseat.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.rocketseat.rocketia.domain.usecase.GetSelectedStackUseCase
import com.rocketseat.rocketia.ui.event.AIChatHistoryEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AIChatHistoryViewModel(
    getSelectedStackUseCase: GetSelectedStackUseCase,
    private val getAIChatHistoryBySelectedStackUseCase: GetAIChatBySelectedStackUseCase
) : ViewModel() {

    val selectedStack: StateFlow<String?> = getSelectedStackUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), null
    )

    private val _selectedStackChipId = MutableStateFlow<Int?>(null)
    val selectedStackChipId: StateFlow<Int?> = _selectedStackChipId.asStateFlow()

    private val _aiChatHistoryBySelectedStack: MutableStateFlow<List<AIChatText>> = MutableStateFlow(emptyList())
    val aiChatHistoryBySelectedStack: StateFlow<List<AIChatText>> = _aiChatHistoryBySelectedStack.asStateFlow()

    fun onEvent(event: AIChatHistoryEvent) {
        when (event) {
            is AIChatHistoryEvent.SelectStack -> getAIChatHistoryBySelectedStack(
                selectedStackName = event.selectedStackName,
                event.selectedStackChipId
            )
        }
    }

    private fun getAIChatHistoryBySelectedStack(selectedStackName: String, selectedStackChipId: Int) {
        viewModelScope.launch {
            val aiChatBySelectedStack = getAIChatHistoryBySelectedStackUseCase(stack = selectedStackName)
            _aiChatHistoryBySelectedStack.update { aiChatBySelectedStack }.also {
                _selectedStackChipId.update { selectedStackChipId }
            }
        }
    }

}