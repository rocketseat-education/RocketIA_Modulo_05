package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAIChatBySelectedStackUseCase @Inject constructor(
    private val repository: AIChatRepository
) {
    operator fun invoke(): Flow<List<AIChatText>> = repository.aiChatBySelectedStack
}