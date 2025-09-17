package com.rocketseat.rocketia.ui.fragment

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.rocketia.ui.viewmodel.AIChatHistoryViewModel
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AIChatHistoryFragmentTest {

    private val viewModel: AIChatHistoryViewModel = mockk(relaxed = true)

    @Test
    fun given_lunch_fragment_when_displayed_then_all_stack_chips_should_be_displayed() {

    }

    @Test
    fun given_user_select_a_stack_when_chip_is_clicked_then_trigger_an_event_and_only_check_the_selected_chip() {

    }

    @Test
    fun given_user_select_a_stack_with_conversation_when_conversation_is_received_then_should_show_all_the_questions_and_answers() {

    }
}