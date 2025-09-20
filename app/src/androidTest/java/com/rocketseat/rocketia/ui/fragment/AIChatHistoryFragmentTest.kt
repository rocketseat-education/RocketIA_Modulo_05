package com.rocketseat.rocketia.ui.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.ui.utils.withFlowContaining
import com.rocketseat.rocketia.ui.viewmodel.AIChatHistoryViewModel
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AIChatHistoryFragmentTest {

    private val viewModel: AIChatHistoryViewModel = mockk(relaxed = true)

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

    }

    @Test
    fun given_user_select_a_stack_with_conversation_when_conversation_is_received_then_should_show_all_the_questions_and_answers() {

    }
}