package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.data.createAIChatTextEntityStub
import com.rocketseat.rocketia.data.mapper.toDomain
import com.rocketseat.rocketia.domain.model.AIChatTextType
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import com.rocketseat.rocketia.domain.util.formatDatetime
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.Locale

class GetAIChatBySelectedStackWithAdditionalInfoUseCaseTest {

    private val aiChatRepository = mockk<AIChatRepository>()
    private val aiAdditionalInfoRepository = mockk<AIAdditionalInfoRepository>()

    private lateinit var useCase: GetAIChatBySelectedStackWithAdditionalInfoUseCase

    @Before
    fun setUp() {
        useCase = GetAIChatBySelectedStackWithAdditionalInfoUseCase(
            aiChatRepository = aiChatRepository,
            aiAdditionalInfoRepository = aiAdditionalInfoRepository
        )
    }

    @Test
    fun `GIVEN additional info not received WHEN invoked THEN should return same chat conversation`() =
        runTest {
            // GIVEN
            val dummyStack = "Kotlin"
            val dummyChatConversation = listOf(
                createAIChatTextEntityStub(
                    from = AIChatTextType.AI_ANSWER,
                    stack = dummyStack,
                    text = "answer"
                )
            ).toDomain()
            val expectedChatTextWithoutAdditionalInfo = AIChatTextWithAdditionalInfo(
                datetimeText = null,
                chatText = dummyChatConversation[0],
                additionalInfo = null
            )

            coEvery { aiChatRepository.getAIChatByStack(stack = any()) } returns dummyChatConversation
            coEvery { aiAdditionalInfoRepository.getAdditionalInfo(answer = any()) } returns null

            // WHEN
            val result = useCase(stack = dummyStack)
            val firstItem = result[0]

            //THEN
            assertEquals(1, result.size)
            assertEquals(expectedChatTextWithoutAdditionalInfo, firstItem)
        }

    @Test
    fun `GIVEN additional info received WHEN invoked THEN should return updated chat conversation`() =
        runTest {
            // GIVEN
            val dummyStack = "Kotlin"
            val dummyChatConversation = listOf(
                createAIChatTextEntityStub(
                    from = AIChatTextType.AI_ANSWER,
                    stack = dummyStack,
                    text = "answer"
                )
            ).toDomain()
            val dummyLocale = Locale.forLanguageTag("pt-BR")
            val expectedAdditionalInfo = AIAdditionalInfo(
                datetime = 1756295438711L,
                text = "additional info"
            )
            val expectedChatTextWithAdditionalInfo = AIChatTextWithAdditionalInfo(
                datetimeText = expectedAdditionalInfo.datetime.formatDatetime(locale = dummyLocale),
                chatText = dummyChatConversation[0],
                additionalInfo = expectedAdditionalInfo
            )

            coEvery { aiChatRepository.getAIChatByStack(stack = any()) } returns dummyChatConversation
            coEvery { aiAdditionalInfoRepository.getAdditionalInfo(answer = any()) } returns expectedAdditionalInfo

            // WHEN
            val result = useCase(stack = dummyStack)
            val firstItem = result[0]

            //THEN
            assertEquals(1, result.size)
            assertNotNull(expectedChatTextWithAdditionalInfo.additionalInfo)
            assertEquals(expectedChatTextWithAdditionalInfo, firstItem)
        }

    @Test
    fun `GIVEN just user question chat text WHEN invoked THEN should return same chat conversation`() =
        runTest {
            // GIVEN
            val dummyStack = "Kotlin"
            val dummyChatConversation = listOf(
                createAIChatTextEntityStub(
                    from = AIChatTextType.USER_QUESTION,
                    stack = dummyStack,
                    text = "answer"
                )
            ).toDomain()
            val expectedChatTextWithoutAdditionalInfo = AIChatTextWithAdditionalInfo(
                datetimeText = null,
                chatText = dummyChatConversation[0],
                additionalInfo = null
            )

            coEvery { aiChatRepository.getAIChatByStack(stack = any()) } returns dummyChatConversation
            coEvery { aiAdditionalInfoRepository.getAdditionalInfo(answer = any()) } returns null

            // WHEN
            val result = useCase(stack = dummyStack)
            val firstItem = result[0]

            //THEN
            assertEquals(1, result.size)
            assertEquals(expectedChatTextWithoutAdditionalInfo, firstItem)
        }

}