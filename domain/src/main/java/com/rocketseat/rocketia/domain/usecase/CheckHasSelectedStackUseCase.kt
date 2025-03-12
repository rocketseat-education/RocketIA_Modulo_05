package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.firstOrNull

class CheckHasSelectedStackUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(): Boolean =
        repository.selectedStack.firstOrNull()?.isNotEmpty() == true

}