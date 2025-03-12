package com.rocketseat.rocketia.ui.event

sealed interface AIChatEvent {
    data class SendUserQuestionToAI(val question: String): AIChatEvent
}