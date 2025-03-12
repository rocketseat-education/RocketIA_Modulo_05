package com.rocketseat.rocketia.data.api

interface AIAPIService {

    suspend fun sendPrompt(stack: String, question: String): String?

}