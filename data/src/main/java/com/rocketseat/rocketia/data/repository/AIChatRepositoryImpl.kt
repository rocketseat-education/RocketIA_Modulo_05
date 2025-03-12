package com.rocketseat.rocketia.data.repository

import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.data.mapper.toDomain
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.model.AIChatTextType
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AIChatRepositoryImpl(
    private val aiChatLocalDataSource: AIChatLocalDataSource,
    private val aiChatRemoteDataSource: AIChatRemoteDataSource
) : AIChatRepository {

    override val selectedStack: Flow<String?>
        get() = aiChatLocalDataSource.selectedStack

    override val aiChatBySelectedStack: Flow<List<AIChatText>>
        get() = aiChatLocalDataSource.aiCurrentChatBySelectedStack.map { currentChatEntity ->
            currentChatEntity.toDomain()
        }

    override suspend fun sendUserQuestion(question: String) {
        val currentSelectedStack = selectedStack.firstOrNull().orEmpty()
        val answer =
            aiChatRemoteDataSource.sendPrompt(question = question, stack = currentSelectedStack)

        answer?.let {
            aiChatLocalDataSource.insertAIChatConversation(
                question = createUserQuestionEntity(
                    question = question,
                    stack = currentSelectedStack
                ),
                answer = createAIAnswerEntity(answer = answer, stack = currentSelectedStack),
            )
        }
    }

    private fun createUserQuestionEntity(question: String, stack: String): AIChatTextEntity =
        AIChatTextEntity(
            stack = stack,
            text = question,
            from = AIChatTextType.USER_QUESTION.name,
            datetime = System.currentTimeMillis()
        )

    private fun createAIAnswerEntity(answer: String, stack: String): AIChatTextEntity =
        AIChatTextEntity(
            stack = stack,
            text = answer,
            from = AIChatTextType.AI_ANSWER.name,
            datetime = System.currentTimeMillis()
        )

    override suspend fun changeStack(stack: String) {
        aiChatLocalDataSource.changeSelectedStack(stack = stack)
    }

    override suspend fun getAIChatByStack(stack: String): List<AIChatText> =
        aiChatLocalDataSource.getAIChatByStack(stack = stack).toDomain()

}