package com.rocketseat.rocketia.data.repository

import app.cash.turbine.test
import com.rocketseat.rocketia.data.createAIChatTextEntityStub
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.domain.model.AIChatTextType
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import io.mockk.coVerify
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AIChatRepositoryImplTest {

    private lateinit var repository: AIChatRepository

    private lateinit var localDataSource: AIChatLocalDataSource
    private lateinit var remoteDataSource: AIChatRemoteDataSource

    fun setUp(
        spyLocal: Boolean = false,
        spyRemote: Boolean = false,
        shouldEmitAIAnswerNull: Boolean = false,
        shouldEmitAIAnswerError: Boolean = false
    ) {
        val fakeRemoteDataSourceImplInstance = FakeAIChatRemoteDataSourceImpl(
            shouldEmitNull = shouldEmitAIAnswerNull,
            shouldEmitError = shouldEmitAIAnswerError
        )

        localDataSource =
            if (spyLocal) spyk(FakeAIChatLocalDataSourceImpl()) else FakeAIChatLocalDataSourceImpl()
        remoteDataSource =
            if (spyRemote) spyk(fakeRemoteDataSourceImplInstance) else fakeRemoteDataSourceImplInstance

        repository = AIChatRepositoryImpl(
            aiChatLocalDataSource = localDataSource,
            aiChatRemoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `GIVEN AI remote answer is null WHEN send user question THEN should not insert chat conversation`() =
        runTest {
            setUp(spyLocal = true, shouldEmitAIAnswerNull = true)

            // GIVEN (dado que...)
            val dummyQuestion = "question"

            // WHEN (quando...)
            repository.sendUserQuestion(question = dummyQuestion)
            // fakeRemoteDataSource returns null for ai answer

            // THEN (então...)
            coVerify(exactly = 0) { localDataSource.insertAIChatConversation(any(), any()) }
            repository.aiChatBySelectedStack.test {
                val result = awaitItem()
                assert(result.isEmpty())
            }
        }

    @Test
    fun `GIVEN AI remote answer is not null WHEN send user question THEN should insert chat conversation`() =
        runTest {
            setUp(spyLocal = true)

            // GIVEN (dado que...)
            val dummyQuestion = "question"

            // WHEN (quando...)
            repository.sendUserQuestion(question = dummyQuestion)
            // fakeRemoteDataSource returns not null for ai answer

            // THEN (então...)
            coVerify(exactly = 1) { localDataSource.insertAIChatConversation(any(), any()) }
            repository.aiChatBySelectedStack.test {
                val result = awaitItem()
                assertEquals(2, result.size)
            }
        }

    @Test
    fun `GIVEN change stack WHEN executed THEN should change selected stack`() = runTest {
        setUp()

        // GIVEN (dado que...)
        val dummyStack = "Kotlin"

        // WHEN (quando...)
        repository.changeStack(stack = dummyStack)

        // THEN (então...)
        repository.selectedStack.test {
            val result = awaitItem()
            assertEquals(dummyStack, result)
        }
    }

    @Test
    fun `GIVEN get chat conversation by stack WHEN is not empty THEN should return chat conversation by stack`() =
        runTest {
            setUp()

            // GIVEN (dado que...)
            val dummyStack = "Kotlin"
            val dummyChatConversation = listOf(
                createAIChatTextEntityStub(
                    from = AIChatTextType.USER_QUESTION,
                    stack = dummyStack,
                    text = "question"
                ),
                createAIChatTextEntityStub(
                    from = AIChatTextType.AI_ANSWER,
                    stack = dummyStack,
                    text = "answer"
                )
            )
            localDataSource.insertAIChatConversation(
                question = dummyChatConversation[0],
                answer = dummyChatConversation[1]
            )

            // WHEN (quando...)
            val result = repository.getAIChatByStack(stack = dummyStack)

            // THEN (então...)
            assertEquals(2, result.size)
        }

    @Test
    fun `GIVEN get chat conversation by stack WHEN is empty THEN should return empty chat conversation`() = runTest {
        setUp()

        // GIVEN (dado que...)
        val dummyStack = "Swift"
        val dummyChatConversation = listOf(
            createAIChatTextEntityStub(
                from = AIChatTextType.USER_QUESTION,
                stack = "Kotlin",
                text = "question"
            ),
            createAIChatTextEntityStub(
                from = AIChatTextType.AI_ANSWER,
                stack = "Kotlin",
                text = "answer"
            )
        )
        localDataSource.insertAIChatConversation(
            question = dummyChatConversation[0],
            answer = dummyChatConversation[1]
        )

        // WHEN (quando...)
        val result = repository.getAIChatByStack(stack = dummyStack)

        // THEN (então...)
        assertEquals(0, result.size)
    }

}