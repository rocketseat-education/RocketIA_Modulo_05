package com.rocketseat.rocketia.data.datasource

class FakeAIChatRemoteDataSourceImpl(
    private val shouldEmitError: Boolean = false,
    private val shouldEmitNull: Boolean = false
) : AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? {
        return if (shouldEmitError)
            throw Exception("HTTP error exception")
        else if (shouldEmitNull)
            null
        else
            "answer for $question from stack $stack"
    }
}