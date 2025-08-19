package com.rocketseat.rocketia

import com.rocketseat.rocketia.data.datasource.AIChatLocalDataSource
import com.rocketseat.rocketia.data.datasource.FakeAIChatRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
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
    fun example_fake_dummy_and_stub() {
        val fakeAIChatRemoteDataSourceImpl = FakeAIChatRemoteDataSourceImpl(validStacks = listOf("Kotlin"))
        val stubAIChatLocalDataSourceImpl = mockk<AIChatLocalDataSource>()
        coEvery { stubAIChatLocalDataSourceImpl.getAIChatByStack(any()) } returns listOf()
    }
}