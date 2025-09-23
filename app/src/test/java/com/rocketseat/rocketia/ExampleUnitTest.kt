package com.rocketseat.rocketia

import android.widget.Toast
import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.AIChatRemoteDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatLocalDataSourceImpl
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import com.rocketseat.rocketia.data.local.database.AIChatTextEntity
import com.rocketseat.rocketia.data.repository.AIChatRepositoryImpl
import com.rocketseat.rocketia.domain.model.AIChatTextType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExampleUnitTest {

    // fake: implementação real porém simplificada de uma interface
    // dummy: objeto criado apenas para preencher parâmetors obrigatórios
    // stub: objeto que retorna valores fixos, SEM verificar interações
    @Test
    fun example_fake_dummy_and_stub() = runTest {
        val fakeAIChatRemoteDataSourceImpl = FakeAIChatRemoteDataSourceImpl()
        val dummyAIChatTextEntity = AIChatTextEntity(
            from = AIChatTextType.USER_QUESTION.name,
            stack = "stack",
            datetime = 0L,
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

    // mock: objeto configurado para retornar valores fixos, possibilitando verificar interaçÕes
    // spy: wrapper sobre objeto real que registra suas interações
    @Test
    fun example_mock_and_spy() = runTest {
        val fakeAIChatLocalDataSourceImpl = FakeAIChatLocalDataSourceImpl()

        val mockAIChatRemoteDataSourceImpl = mockk<AIChatRemoteDataSource>(relaxed = true)
        val spyAIChatLocalDataSourceImpl = spyk<AIChatLocalDataSource>(fakeAIChatLocalDataSourceImpl)

        val testRepository = AIChatRepositoryImpl(
            aiChatLocalDataSource = spyAIChatLocalDataSourceImpl,
            aiChatRemoteDataSource = mockAIChatRemoteDataSourceImpl
        )

        testRepository.sendUserQuestion("question")

        coVerify(exactly = 1) { mockAIChatRemoteDataSourceImpl.sendPrompt(any(), any()) }
        coVerify(exactly = 1) { spyAIChatLocalDataSourceImpl.insertAIChatConversation(any(), any()) }
    }

    // shadow: fake específico completo (Roboletric) que substitui um conjunto de classes (framework Android)
    @Test
    fun example_shadow() {
        val context = RuntimeEnvironment.getApplication()

        Toast.makeText(context, "Hello world!", Toast.LENGTH_SHORT).show()

        assertEquals("Hello world!", ShadowToast.getTextOfLatestToast())
    }
}