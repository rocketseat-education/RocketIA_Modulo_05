package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import com.rocketseat.rocketia.domain.util.formatDatetime

data class AIChatTextWithAdditionalInfo(
    val datetime: String?,
    val chatText: AIChatText,
    val additionalInfo: AIAdditionalInfo?
)

data class AIAdditionalInfo(
    val datetime: Long,
    val text: String?
)

interface AIAdditionalInfoRepository {
    fun getAdditionalInfo(answer: String): AIAdditionalInfo?
}

class GetAIChatBySelectedStackWithAdditionalInfoUseCase(
    private val aiChatRepository: AIChatRepository,
    private val aiAdditionalInfoRepository: AIAdditionalInfoRepository
) {

    suspend operator fun invoke(stack: String): List<AIChatTextWithAdditionalInfo> {
        return aiChatRepository.getAIChatByStack(stack).map { aiChatText ->
            when (aiChatText) {
                is AIChatText.AIAnswer -> {
                    val additionalInfo =
                        aiAdditionalInfoRepository.getAdditionalInfo(answer = aiChatText.answer)
                    AIChatTextWithAdditionalInfo(
                        datetime = additionalInfo?.datetime?.formatDatetime(),
                        chatText = aiChatText,
                        additionalInfo = additionalInfo
                    )
                }
                else -> {
                    AIChatTextWithAdditionalInfo(
                        datetime = null,
                        chatText = aiChatText,
                        additionalInfo = null
                    )
                }
            }
        }
    }
}