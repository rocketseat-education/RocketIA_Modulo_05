package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedStackUseCase @Inject constructor(
    private val repository: AIChatRepository
) {
    operator fun invoke(): Flow<String?> = repository.selectedStack
}