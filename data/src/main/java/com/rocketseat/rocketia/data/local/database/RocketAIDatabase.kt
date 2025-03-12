package com.rocketseat.rocketia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val ROCKET_AI_DATABASE_NAME = "rocket_ai_db"

@Database(entities = [AIChatTextEntity::class], version = 1)
abstract class RocketAIDatabase: RoomDatabase() {

    abstract fun aiChatHistoryDao() : AIChatHistoryDao

}