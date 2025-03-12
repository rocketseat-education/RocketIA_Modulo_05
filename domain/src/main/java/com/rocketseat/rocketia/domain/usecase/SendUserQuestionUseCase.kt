package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository

class SendUserQuestionUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(question: String) {
        repository.sendUserQuestion(question = question)
    }

}