package com.rocketseat.rocketia.data

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.domain.model.AIChatTextType

fun createAIChatTextEntityStub(from: AIChatTextType?, text: String = "text", stack: String = "stack"): AIChatTextEntity =
    AIChatTextEntity(
        from = from?.name ?: "UNKNOWN",
        stack = stack,
        text = text,
        dateTime = 0L
    )