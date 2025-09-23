package com.rocketseat.rocketia.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AIChatTextEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val from: String,
    val stack: String,
    val datetime: Long,
    val text: String
)
