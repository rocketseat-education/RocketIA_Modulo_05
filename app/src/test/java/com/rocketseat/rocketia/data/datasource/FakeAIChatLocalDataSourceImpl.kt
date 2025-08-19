package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAIChatLocalDataSourceImpl: AIChatLocalDataSource {
    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = flowOf()

    private val chatConversationList = mutableListOf<AIChatTextEntity>()

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        chatConversationList.add(question)
        chatConversationList.add(answer)
    }

    override val selectedStack: Flow<String?>
        get() = flowOf()

    override suspend fun changeSelectedStack(stack: String) {}

    override suspend fun getAIChatByStack(stack: String): List<AIChatTextEntity> {
        return chatConversationList.filter { it.stack == stack }
    }

}