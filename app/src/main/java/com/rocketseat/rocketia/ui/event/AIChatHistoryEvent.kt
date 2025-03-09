package com.rocketseat.rocketia.ui.event

sealed interface AIChatHistoryEvent {
    data class GetAIChatHistoryBySelectedStack(val stack: String): AIChatHistoryEvent
}