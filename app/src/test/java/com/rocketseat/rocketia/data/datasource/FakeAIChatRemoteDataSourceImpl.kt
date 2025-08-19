package com.rocketseat.rocketia.data.datasource

class FakeAIChatRemoteDataSourceImpl(
    private val validStacks: List<String>
) : AIChatRemoteDataSource {

    private var shouldEmitError: Boolean = false

    fun emitError(shouldEmitError: Boolean) {
        this.shouldEmitError = shouldEmitError
    }

    override suspend fun sendPrompt(stack: String, question: String): String? {
        return if (shouldEmitError)
            throw Exception("HTTP error exception")
        else if (validStacks.contains(stack))
            "Valid answer"
        else
            null
    }
}