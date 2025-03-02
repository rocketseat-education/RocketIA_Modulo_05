package com.rocketseat.rocketia.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AIChatHistoryDao {

    @Query("SELECT * FROM aiChattextentity WHERE stack = :stack ORDER BY dateTime DESC")
    fun getAllByStack(stack: String): Flow<List<AIChatTextEntity>>

    @Insert
    suspend fun insetAll(vararg aiChatText: AIChatTextEntity)
}