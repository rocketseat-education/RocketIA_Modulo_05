package com.rocketseat.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.rocketia.domain.usecase.ChangeStackUseCase
import com.rocketseat.rocketia.ui.event.ChooseStackUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChooseStackViewModel(
    private val changeStackUseCase: ChangeStackUseCase
) : ViewModel() {

    private val _selectedStackChipId = MutableStateFlow<Int?>(null)
    val selectedStackChipId: StateFlow<Int?> = _selectedStackChipId.asStateFlow()

    private val _isConfirmedNewStack = MutableStateFlow<Boolean>(false)
    val isConfirmedNewStack: StateFlow<Boolean> = _isConfirmedNewStack.asStateFlow()

    fun onEvent(event: ChooseStackUiEvent) {
        when (event) {
            is ChooseStackUiEvent.SelectStack -> selectStack(
                selectedStackName = event.selectedStackName,
                selectedStackChipId = event.selectedStackChipId
            )
        }
    }

    private fun selectStack(selectedStackName: String, selectedStackChipId: Int) {
        _isConfirmedNewStack.update { true }
        viewModelScope.launch {
            changeStackUseCase(stack = selectedStackName)
        }
        _selectedStackChipId.update { selectedStackChipId }
    }

}