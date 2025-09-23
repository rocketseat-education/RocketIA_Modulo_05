package com.rocketseat.rocketia.data.datasource

interface AIChatRemoteDataSource {

    suspend fun sendPrompt(stack: String, question: String): String?

}