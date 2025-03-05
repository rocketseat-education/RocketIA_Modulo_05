package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository
import javax.inject.Inject

class SendUserQuestionUseCase @Inject constructor(
    private val repository: AIChatRepository
) {
    suspend operator fun invoke(question: String) {
        repository.sendUserQuestion(question = question)
    }
}