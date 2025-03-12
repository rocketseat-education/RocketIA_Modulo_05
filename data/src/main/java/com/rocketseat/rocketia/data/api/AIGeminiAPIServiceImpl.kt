package com.rocketseat.rocketia.data.api

import com.rocketseat.rocketia.data.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

private const val GENERATIVE_MODEL_NAME = "gemini-1.5-flash"

class AIGeminiAPIServiceImpl : AIAPIService {

    private val generativeModel = GenerativeModel(
        modelName = GENERATIVE_MODEL_NAME,
        apiKey = BuildConfig.apiKey
    )

    override suspend fun sendPrompt(stack: String, question: String): String? =
        try {
            val customPrompt = generatePrompt(stack, question)
            val response = generativeModel.generateContent(prompt = customPrompt)
            response.text
        } catch (_: Exception) {
            null
        }

    private fun generatePrompt(stack: String, question: String): String =
        """
            Você é um assistente de programação especializado, capaz de responder a perguntas 
            técnicas de desenvolvimento de software em diversas stacks de tecnologia.

            Sua tarefa é auxiliar desenvolvedores fornecendo:
            
            1) Explicações claras e objetivas.
            2) Trechos de código bem estruturados, seguindo as melhores práticas.
            3) Soluções alternativas ou otimizações, quando aplicável.
            4) Referências à documentação oficial ou a fontes confiáveis, quando relevante.
            
            Aqui está a pergunta do(a) desenvolvedor(a): $question
            A stack de tecnologia selecionada é: $stack
            
            Forneça uma resposta detalhada, didática, precisa e prática para ajudar o desenvolvedor 
            a resolver sua dúvida de forma eficiente.
        """.trimIndent()

}