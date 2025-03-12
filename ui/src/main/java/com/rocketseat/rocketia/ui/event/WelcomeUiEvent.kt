package com.rocketseat.rocketia.ui.event

sealed interface WelcomeUiEvent {
    object CheckHasSelectedStack: WelcomeUiEvent
}