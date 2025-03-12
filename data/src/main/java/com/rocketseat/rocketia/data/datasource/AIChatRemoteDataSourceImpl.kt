package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.api.AIAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AIChatRemoteDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val aiApiService: AIAPIService
): AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? = withContext(ioDispatcher) {
        aiApiService.sendPrompt(stack, question)
    }

}