package com.rocketseat.rocketia.data.datasource

import com.rocketseat.rocketia.data.api.AIAPIService

class AIChatRemoteDataSourceImpl(
    private val aiApiService: AIAPIService
) : AIChatRemoteDataSource {
    override suspend fun sendPrompt(stack: String, question: String): String? =
        aiApiService.sendPrompt(stack, question)
}