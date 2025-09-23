package com.rocketseat.rocketia.ui.view

import android.view.LayoutInflater
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.rocketseat.rocketia.databinding.FragmentAiChatHistoryBinding
import org.junit.Rule
import org.junit.Test

class AIChatHistoryFragmentSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "Theme.RocketIA.Paparazzi",
        appCompatEnabled = true
    )

    @Test
    fun `AIChatHistoryFragment snapshot test`() {
        val view = FragmentAiChatHistoryBinding.inflate(LayoutInflater.from(paparazzi.context))

        paparazzi.snapshot(view = view.root, name = "ai_chat_history_fragment_snapshot")
    }

}