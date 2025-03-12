package com.rocketseat.rocketia.data.mapper

import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.domain.model.AIChatTextType

fun AIChatTextEntity.toDomain() : AIChatText =
    when(this.from) {
        AIChatTextType.USER_QUESTION.name -> AIChatText.UserQuestion(question = this.text)
        AIChatTextType.AI_ANSWER.name -> AIChatText.AIAnswer(answer = this.text)
        else -> throw IllegalArgumentException("Invalid from value: ${this.from}")
    }

fun List<AIChatTextEntity>.toDomain() : List<AIChatText> =
    this.map { entity -> entity.toDomain() }