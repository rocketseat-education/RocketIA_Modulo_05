package com.rocketseat.rocketia.data.repository

import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.domain.repository.AIChatRepository

class AIChatRepositoryImplTest {

    private lateinit var repository: AIChatRepository

    private lateinit var localDataSource: AIChatLocalDataSource
    private lateinit var remoteDataSource: AIChatRemoteDataSource

    fun setup() {
        localDataSource = FakeAIChatLocalDataSourceImpl()
        remoteDataSource = FakeAIChatRemoteDataSourceImpl(validStacks = emptyList())

        repository = AIChatRepositoryImpl(
            aiChatLocalDataSource = localDataSource,
            aiChatRemoteDataSource = remoteDataSource
        )
    }
}