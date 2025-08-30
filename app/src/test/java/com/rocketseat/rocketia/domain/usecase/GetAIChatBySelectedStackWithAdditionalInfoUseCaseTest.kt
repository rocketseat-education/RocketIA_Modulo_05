package com.rocketseat.rocketia.domain.usecase

import com.rocketseat.rocketia.domain.repository.AIChatRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class GetAIChatBySelectedStackWithAdditionalInfoUseCaseTest {

    private val aiChatRepository = mockk<AIChatRepository>()
    private val aiAdditionalInfoRepository = mockk<AIAdditionalInfoRepository>()

    private lateinit var useCase: GetAIChatBySelectedStackWithAdditionalInfoUseCase

    @Before
    fun setup() {
        useCase = GetAIChatBySelectedStackWithAdditionalInfoUseCase(
            aiChatRepository = aiChatRepository,
            aiAdditionalInfoRepository = aiAdditionalInfoRepository
        )
    }

    @Test
    fun `GIVEN additional info not received WHEN invoked THEN should return same chat conversation`() {
        // GIVEN
        // WHEN
        // THEN
    }

    @Test
    fun `GIVEN additional info received WHEN invoked THEN should return update chat conversation`() {
        // GIVEN
        // WHEN
        // THEN
    }

    @Test
    fun `GIVEN just user question chat text WHEN invoked THEN should return same chat conversation`() {
        // GIVEN
        // WHEN
        // THEN
    }
}