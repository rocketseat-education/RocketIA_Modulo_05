package com.rocketseat.rocketia

import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.data.repository.AIChatRepositoryImpl
import com.rocketseat.rocketia.domain.model.AIChatTextType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    // fake: implementação real porém simplificada de uma interface
    // dummy: objeto criado apenas para preencher parâmetros obrigatórios
    // stub: objeto que retorna valores fixos, SEM verificar interações
    @Test
    fun example_fake_dummy_and_stub() = runTest {
        val fakeAIChatRemoteDataSourceImpl = FakeAIChatRemoteDataSourceImpl(validStacks = listOf("Kotlin"))
        val dummyAIChatTextEntity = AIChatTextEntity(
            from = AIChatTextType.USER_QUESTION.name,
            stack = "stack",
            dateTime = 0L,
            text = "text"
        )
        val dummyAIChatTextEntityList = listOf(dummyAIChatTextEntity, dummyAIChatTextEntity, dummyAIChatTextEntity)
        val stubAIChatLocalDataSourceImpl = mockk<AIChatLocalDataSource>()
        coEvery { stubAIChatLocalDataSourceImpl.getAIChatByStack(any()) } returns dummyAIChatTextEntityList

        val testRepository = AIChatRepositoryImpl(
            aiChatLocalDataSource = stubAIChatLocalDataSourceImpl,
            aiChatRemoteDataSource = fakeAIChatRemoteDataSourceImpl
        )

        val result = testRepository.getAIChatByStack(stack = "Java")

        coVerify(exactly = 1) { stubAIChatLocalDataSourceImpl.getAIChatByStack(stack = "Java") }
        assertEquals(3, result.size)
    }
}