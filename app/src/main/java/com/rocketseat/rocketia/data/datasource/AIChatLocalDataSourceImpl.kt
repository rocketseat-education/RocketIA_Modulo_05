package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.local.database.AIChatHistoryDao
import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.data.local.preferences.UserSettingsPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AIChatLocalDataSourceImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val aiChatHistoryDao: AIChatHistoryDao,
    private val userSettingsPreferences: UserSettingsPreferences
) : AIChatLocalDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = userSettingsPreferences.selectedStack.flatMapLatest { selectedStack ->
            aiChatHistoryDao.getAllByStack(stack = selectedStack.orEmpty())
        }.flowOn(ioDispatcher)

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        withContext(ioDispatcher) {
            aiChatHistoryDao.insetAll(question, answer)
        }
    }

    override val selectedStack: Flow<String?>
        get() = userSettingsPreferences.selectedStack.flowOn(ioDispatcher)

    override suspend fun changeSelectedStack(stack: String) {
        withContext(ioDispatcher) {
            userSettingsPreferences.changeSelectedStack(stack)
        }
    }
}