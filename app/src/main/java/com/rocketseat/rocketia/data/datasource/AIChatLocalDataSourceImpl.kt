package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow

class AIChatLocalDataSourceImpl: AIChatLocalDataSource {

    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = TODO("Not yet implemented")

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        TODO("Not yet implemented")
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