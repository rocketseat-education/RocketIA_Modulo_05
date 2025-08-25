package com.rocketseat.rocketia.data.repository

import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import io.mockk.spyk

class AIChatRepositoryImplTest {

    private lateinit var repository: AIChatRepository

    private lateinit var localDataSource: AIChatLocalDataSource
    private lateinit var remoteDataSource: AIChatRemoteDataSource

    fun setup(
        spyLocal: Boolean = false,
        spyRemote: Boolean = false,
        shouldEmitAIAnswerNull: Boolean = false,
        shouldEmitAIAnswerError: Boolean = false
    ) {
        val fakeRemoteDataSourceImplInstance = FakeAIChatRemoteDataSourceImpl(
            shouldEmitNull = shouldEmitAIAnswerNull,
            shouldEmitError = shouldEmitAIAnswerError
        )

        localDataSource =
            if(spyLocal) spyk(FakeAIChatLocalDataSourceImpl()) else FakeAIChatLocalDataSourceImpl()
        remoteDataSource =
            if(spyRemote) spyk(fakeRemoteDataSourceImplInstance) else fakeRemoteDataSourceImplInstance

        repository = AIChatRepositoryImpl(
            aiChatLocalDataSource = localDataSource,
            aiChatRemoteDataSource = remoteDataSource
        )
    }
}