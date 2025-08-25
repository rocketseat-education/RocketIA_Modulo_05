package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeAIChatLocalDataSourceImpl: AIChatLocalDataSource {
    private val _aiCurrentChatBySelectedStack = MutableStateFlow<List<AIChatTextEntity>>(emptyList())

    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = _aiCurrentChatBySelectedStack.asStateFlow()

    private val _selectedStack = MutableStateFlow<String?>(null)
    override val selectedStack: Flow<String?>
        get() = _selectedStack.asStateFlow()


    private val chatConversationList = mutableListOf<AIChatTextEntity>()

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        chatConversationList.add(question)
        chatConversationList.add(answer)
        _aiCurrentChatBySelectedStack.value = chatConversationList
    }


    override suspend fun changeSelectedStack(stack: String) {
        _selectedStack.value = stack
    }

    override suspend fun getAIChatByStack(stack: String): List<AIChatTextEntity> {
        return chatConversationList.filter { it.stack == stack }
    }

}