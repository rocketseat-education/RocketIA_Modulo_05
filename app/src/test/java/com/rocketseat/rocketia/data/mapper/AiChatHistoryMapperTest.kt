package com.rocketseat.rocketia.data.mapper

import com.rocketseat.rocketia.data.createAIChatTextEntityStub
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.model.AIChatTextType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertThrows

import org.junit.Test

class AiChatHistoryMapperTest {

    @Test
    fun `GIVEN AiChatHistoryEntity from USER QUESTION type WHEN toDomain is called THEN should convert to AIChatText from USER QUESTION`() {
        // GIVEN (dado que...)
        val userQuestionEntity = createAIChatTextEntityStub(from = AIChatTextType.USER_QUESTION)

        // WHEN (quando...)
        val result = userQuestionEntity.toDomain()

        // THEN (então...)
        assertTrue(result is AIChatText.UserQuestion)
        assertEquals(userQuestionEntity.text, (result as AIChatText.UserQuestion).question)
    }

    @Test
    fun `GIVEN AiChatHistoryEntity from AI ANSWER type WHEN toDomain is called THEN should convert to AIChatText from AI ANSWER`() {
        // GIVEN (dado que...)
        val aiAnswerEntity = createAIChatTextEntityStub(from = AIChatTextType.AI_ANSWER)

        // WHEN (quando...)
        val result = aiAnswerEntity.toDomain()

        // THEN (então...)
        assertTrue(result is AIChatText.AIAnswer)
        assertEquals(aiAnswerEntity.text, (result as AIChatText.AIAnswer).answer)
    }

    @Test
    fun `GIVEN AiChatHistoryEntity from UNKNOWN type WHEN toDomain is called THEN should trigger an exception`() {
        // GIVEN (dado que...)
        val unknownEntity = createAIChatTextEntityStub(from = null)

        // WHEN (quando...)
        val result = assertThrows(IllegalArgumentException::class.java) {
            unknownEntity.toDomain()
        }

        // THEN (então...)
        assertEquals("Invalid from value: UNKNOWN", result.message)
    }

    @Test
    fun `GIVEN AiChatHistoryEntity list WHEN toDomain is called THEN should convert to AIChatText list`() {
        // GIVEN (dado que...)
        val aiChatTextEntityList = List(10) { index ->
            createAIChatTextEntityStub(
                from = when {
                    index % 2 == 0 -> AIChatTextType.USER_QUESTION
                    index % 2 != 0 -> AIChatTextType.AI_ANSWER
                    else -> null
                }
            )
        }

        // WHEN (quando...)
        val result = aiChatTextEntityList.toDomain()

        // THEN (então...)
        assertEquals(aiChatTextEntityList.size, result .size)
    }
}