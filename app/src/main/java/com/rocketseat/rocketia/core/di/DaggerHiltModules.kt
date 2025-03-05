package com.rocketseat.rocketia.core.di

import android.content.Context
import androidx.room.Room
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.data.local.database.AIChatHistoryDao
import com.rocketseat.rocketia.data.local.database.ROCKET_AI_DATABASE_NAME
import com.rocketseat.rocketia.data.local.database.RocketAIDatabase
import com.rocketseat.rocketia.data.local.preferences.UserSettingsDataStorePreferencesImpl
import com.rocketseat.rocketia.data.local.preferences.UserSettingsPreferences
import com.rocketseat.rocketia.data.remote.api.AIAPIService
import com.rocketseat.rocketia.data.remote.api.AIGeminiAPIServiceImpl
import com.rocketseat.rocketia.data.repository.AIChatRepositoryImpl
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAIAPIService() : AIAPIService = AIGeminiAPIServiceImpl()

    @Provides
    @Singleton
    fun provideUserSettingsPreferences(
        @ApplicationContext context: Context
    ) : UserSettingsPreferences =
        UserSettingsDataStorePreferencesImpl(
            context = context
        )

    @Provides
    @Singleton
    fun provideRocketAIDatabase(
        @ApplicationContext context: Context
    ): RocketAIDatabase = Room.databaseBuilder(
        context,
        RocketAIDatabase::class.java,
        ROCKET_AI_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideAIChatHistoryDao(
        rocketAIDatabase: RocketAIDatabase
    ): AIChatHistoryDao = rocketAIDatabase.aiChatHistoryDao()

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAIChatLocalDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        aiChatHistoryDao: AIChatHistoryDao,
        userSettingsPreferences: UserSettingsPreferences
    ) : AIChatLocalDataSource = AIChatLocalDataSourceImpl(
        ioDispatcher = ioDispatcher,
        aiChatHistoryDao = aiChatHistoryDao,
        userSettingsPreferences = userSettingsPreferences
    )

    @Provides
    @Singleton
    fun provideAIChatRemoteDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        aiApiService: AIAPIService
    ): AIChatRemoteDataSource = AIChatRemoteDataSourceImpl(
        ioDispatcher = ioDispatcher,
        aiApiService = aiApiService
    )

    @Provides
    @Singleton
    fun provideAIChatRepository(
        aiChatLocalDataSource: AIChatLocalDataSource,
        aiChatRemoteDataSource: AIChatRemoteDataSource
    ): AIChatRepository = AIChatRepositoryImpl(
        aiChatLocalDataSource = aiChatLocalDataSource,
        aiChatRemoteDataSource = aiChatRemoteDataSource,
    )
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher