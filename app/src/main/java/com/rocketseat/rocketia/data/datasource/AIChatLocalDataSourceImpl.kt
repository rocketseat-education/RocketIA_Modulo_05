package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatHistoryDao
import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AIChatLocalDataSourceImpl(
    private val aiChatHistoryDao: AIChatHistoryDao
): AIChatLocalDataSource {

    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = flow {
            val selectedStack = ""
            aiChatHistoryDao.getAllByStack(selectedStack)
        }

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        aiChatHistoryDao.insetAll(question, answer)
    }

    override val selectedStack: Flow<String>
        get() = TODO("Not yet implemented")

    override suspend fun changeSelectedStack(stack: String) {
        TODO("Not yet implemented")
    }

    override val firstLaunch: Flow<Boolean>
        get() = TODO("Not yet implemented")

    override suspend fun changeFirstLaunch() {
        TODO("Not yet implemented")
    }
}