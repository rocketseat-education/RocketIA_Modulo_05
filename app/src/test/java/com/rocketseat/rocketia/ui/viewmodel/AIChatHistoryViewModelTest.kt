package com.rocketseat.rocketia.ui.viewmodel

import app.cash.turbine.test
import com.rocketseat.rocketia.data.createAIChatTextEntityStub
import com.rocketseat.rocketia.data.mapper.toDomain
import com.rocketseat.rocketia.domain.model.AIChatTextType
import com.rocketseat.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.rocketseat.rocketia.domain.usecase.GetSelectedStackUseCase
import com.rocketseat.rocketia.ui.event.AIChatHistoryEvent
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AIChatHistoryViewModelTest {

    private val getSelectedStackUseCase: GetSelectedStackUseCase = mockk()
    private val getIAChatBySelectedStackUseCase: GetAIChatBySelectedStackUseCase = mockk()

    private lateinit var viewModel: AIChatHistoryViewModel

    private val dummyInitialStack = "Kotlin"

    @Before
    fun setup() {
        every { getSelectedStackUseCase() } returns flowOf(dummyInitialStack)
        viewModel = AIChatHistoryViewModel(
            getSelectedStackUseCase = getSelectedStackUseCase,
            getAIChatHistoryBySelectedStackUseCase = getIAChatBySelectedStackUseCase
        )
    }

    @Test
    fun `GIVEN empty history WHEN triggers SelectStack event THEN aiChatHistoryBySelectedStack and stackChipId should be updated`() =
        runTest {
            //GIVEN
            val dummySelectedStackName = "Java"
            val dummySelectedStackChipId = 123
            val stubAIChatHistoryBySelectedStack = listOf(
                createAIChatTextEntityStub(
                    from = AIChatTextType.USER_QUESTION,
                    stack = dummySelectedStackName
                ),
                createAIChatTextEntityStub(
                    from = AIChatTextType.AI_ANSWER,
                    stack = dummySelectedStackName
                )
            ).toDomain()

            coEvery { getIAChatBySelectedStackUseCase(stack = dummySelectedStackName) } returns stubAIChatHistoryBySelectedStack

            //WHEN
            viewModel.onEvent(
                event = AIChatHistoryEvent.SelectStack(
                    selectedStackName = dummySelectedStackName,
                    selectedStackChipId = dummySelectedStackChipId
                )
            )

            //THEN
            viewModel.aiChatHistoryBySelectedStack.test {
                val result = awaitItem()
                assertEquals(stubAIChatHistoryBySelectedStack, result)
            }
            viewModel.selectedStackChipId.test {
                val result = awaitItem()
                assertEquals(dummySelectedStackChipId, result)
            }
        }
}