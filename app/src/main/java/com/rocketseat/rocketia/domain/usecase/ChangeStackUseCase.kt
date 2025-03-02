package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.model.AIChatTextType
import com.rocketseat.rocketia.domain.repository.AIChatRepository

class ChangeStackUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(stack: String) {
        repository.changeStack(stack = stack)
    }
}