package com.rocketseat.rocketia.ui.event

sealed interface ChooseStackUiEvent {
    data class SelectStack(val selectedStackName: String, val selectedStackChipId: Int): ChooseStackUiEvent
}