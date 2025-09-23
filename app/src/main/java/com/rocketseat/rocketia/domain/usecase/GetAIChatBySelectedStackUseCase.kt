package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetAIChatBySelectedStackUseCase(
    private val repository: AIChatRepository
) {

    operator fun invoke(): Flow<List<AIChatText>> = repository.aiChatBySelectedStack

    suspend operator fun invoke(stack: String): List<AIChatText> = repository.getAIChatByStack(stack = stack)

}