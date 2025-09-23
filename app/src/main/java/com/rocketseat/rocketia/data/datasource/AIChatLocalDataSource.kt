package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow

interface AIChatLocalDataSource {

    val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>

    suspend fun insertAIChatConversation(question: AIChatTextEntity, answer: AIChatTextEntity)

    val selectedStack: Flow<String?>

    suspend fun changeSelectedStack(stack: String)

    suspend fun getAIChatByStack(stack: String): List<AIChatTextEntity>

}