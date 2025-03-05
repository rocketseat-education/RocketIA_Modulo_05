package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository
import javax.inject.Inject

class ChangeStackUseCase @Inject constructor(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(stack: String) {
        repository.changeStack(stack = stack)
    }
}