package com.rocketseat.rocketia.di

import androidx.room.Room
import com.rocketseat.rocketia.data.api.AIAPIService
import com.rocketseat.rocketia.data.api.AIGeminiAPIServiceImpl
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.data.local.database.AIChatHistoryDao
import com.rocketseat.rocketia.data.local.database.ROCKET_AI_DATABASE_NAME
import com.rocketseat.rocketia.data.local.database.RocketAIDatabase
import com.rocketseat.rocketia.data.local.preferences.UserSettingsDataStorePreferencesImpl
import com.rocketseat.rocketia.data.local.preferences.UserSettingsPreferences
import com.rocketseat.rocketia.data.repository.AIChatRepositoryImpl
import com.rocketseat.rocketia.domain.repository.AIChatRepository
import com.rocketseat.rocketia.domain.usecase.ChangeStackUseCase
import com.rocketseat.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import com.rocketseat.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.rocketseat.rocketia.domain.usecase.GetSelectedStackUseCase
import com.rocketseat.rocketia.domain.usecase.SendUserQuestionUseCase
import com.rocketseat.rocketia.ui.viewmodel.AIChatHistoryViewModel
import com.rocketseat.rocketia.ui.viewmodel.AIChatViewModel
import com.rocketseat.rocketia.ui.viewmodel.ChooseStackViewModel
import com.rocketseat.rocketia.ui.viewmodel.WelcomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }

    single<AIAPIService> { AIGeminiAPIServiceImpl() }
    single<UserSettingsPreferences> { UserSettingsDataStorePreferencesImpl(context = androidApplication()) }
    single<RocketAIDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            RocketAIDatabase::class.java,
            ROCKET_AI_DATABASE_NAME
        ).build()
    }
    single<AIChatHistoryDao> { get<RocketAIDatabase>().aiChatHistoryDao() }

    single<AIChatLocalDataSource> {
        AIChatLocalDataSourceImpl(
            ioDispatcher = get(named("IO")),
            aiChatHistoryDao = get<AIChatHistoryDao>(),
            userSettingsPreferences = get<UserSettingsPreferences>()
        )
    }
    single<AIChatRemoteDataSource> {
        AIChatRemoteDataSourceImpl(
            ioDispatcher = get(named("IO")),
            aiApiService = get<AIAPIService>()
        )
    }

    single<AIChatRepository> {
        AIChatRepositoryImpl(
            aiChatLocalDataSource = get<AIChatLocalDataSource>(),
            aiChatRemoteDataSource = get<AIChatRemoteDataSource>()
        )
    }
}

val domainModule = module {
    factory { ChangeStackUseCase(repository = get<AIChatRepository>()) }
    factory { CheckHasSelectedStackUseCase(repository = get<AIChatRepository>()) }
    factory { GetAIChatBySelectedStackUseCase(repository = get<AIChatRepository>()) }
    factory { GetSelectedStackUseCase(repository = get<AIChatRepository>()) }
    factory { SendUserQuestionUseCase(repository = get<AIChatRepository>()) }
}

val uiModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::ChooseStackViewModel)
    viewModelOf(::AIChatViewModel)
    viewModelOf(::AIChatHistoryViewModel)
}