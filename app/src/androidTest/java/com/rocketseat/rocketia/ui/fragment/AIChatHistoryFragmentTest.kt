package com.rocketseat.rocketia.ui.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.ui.event.AIChatHistoryEvent
import com.rocketseat.rocketia.ui.utils.withFlowContaining
import com.rocketseat.rocketia.ui.viewmodel.AIChatHistoryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class AIChatHistoryFragmentTest {

    private val mockViewModel: AIChatHistoryViewModel = mockk(relaxed = true)

    private val availableStackChipIds = listOf(
        R.id.chpReactNative,
        R.id.chpIA,
        R.id.chpGo,
        R.id.chpKotlin,
        R.id.chpCSharp,
        R.id.chpPHP,
        R.id.chpDevOps,
        R.id.chpFullStack,
        R.id.chpJava,
        R.id.chpPyton,
        R.id.chpReact,
        R.id.chpNodeJS,
        R.id.chpSwift
    )

    private fun launchTargetFragment() =
        launchFragmentInContainer<AIChatHistoryFragment>(themeResId = R.style.Theme_RocketIA)

    private val selectedStackStub  = MutableStateFlow<String?>(null)
    private val selectedStackChipIdStub  = MutableStateFlow<Int?>(null)
    private val aiChatHistoryBySelectedStackStub = MutableStateFlow<List<AIChatText>>(emptyList())

    @Before
    fun setup() {
        stopKoin()

        every { mockViewModel.selectedStack } returns selectedStackStub.asStateFlow()
        every { mockViewModel.selectedStackChipId } returns selectedStackChipIdStub.asStateFlow()
        every { mockViewModel.aiChatHistoryBySelectedStack } returns aiChatHistoryBySelectedStackStub.asStateFlow()

        startKoin {
            modules(
                module {
                    viewModel<AIChatHistoryViewModel> { mockViewModel }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun given_launch_fragment_when_displayed_then_all_stack_chips_should_be_displayed() {
        // GIVEN
        // WHEN
        launchTargetFragment()

        // THEN
        onView(withId(R.id.flwFilterStackOptions)).check(matches(isDisplayed()))
        onView(withId(R.id.flwFilterStackOptions))
            .check(
                matches(
                    withFlowContaining(*availableStackChipIds.toIntArray())
                )
            )
    }

    @Test
    fun given_user_select_a_stack_when_chip_is_clicked_then_trigger_an_event_and_only_check_the_selected_chip() {
        //GIVEN
        val clickedChipText = "Kotlin"
        val clickedChipId = R.id.chpKotlin
        val expectedEvent = AIChatHistoryEvent.SelectStack(
            selectedStackName = clickedChipText,
            selectedStackChipId = clickedChipId
        )

        val scenario = launchTargetFragment()
        scenario.moveToState(Lifecycle.State.RESUMED)

        //WHEN
        onView(withId(clickedChipId)).perform(ViewActions.scrollTo(), click())

        //THEN
        verify { mockViewModel.onEvent(event = expectedEvent) }
        onView(withId(clickedChipId)).check(matches(isChecked()))
        availableStackChipIds.forEach { stackChipId ->
            if (stackChipId != clickedChipId) {
                onView(withId(stackChipId)).check(matches(isNotChecked()))
            }
        }
    }

    @Test
    fun given_user_select_a_stack_with_conversation_when_conversation_is_received_then_should_show_all_the_questions_and_answers() {
        //GIVEN
        val dummyQuestion = "question"
        val dummyAnswer = "answer"
        val expectedConversation = listOf(
            AIChatText.UserQuestion(question = dummyQuestion),
            AIChatText.AIAnswer(answer = dummyAnswer)
        )

        val scenario = launchTargetFragment()
        scenario.moveToState(Lifecycle.State.RESUMED)

        //WHEN
        scenario.onFragment { fragment ->
            fragment.requireActivity().runOnUiThread {
                aiChatHistoryBySelectedStackStub.value = expectedConversation
            }
        }

        //THEN
        onView(withText(dummyQuestion)).check(matches(isDisplayed()))
        onView(withText(dummyAnswer)).check(matches(isDisplayed()))
    }
}