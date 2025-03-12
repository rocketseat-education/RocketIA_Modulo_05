package com.rocketseat.rocketia.ui.event

sealed interface AIChatHistoryEvent {
    data class SelectStack(val selectedStackName: String, val selectedStackChipId: Int): AIChatHistoryEvent
}