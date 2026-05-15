package com.syeda.raithabharosa.utils

import com.google.ai.client.generativeai.GenerativeModel

object GeminiApi {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash-lite",
        apiKey = "AIzaSyAK8w90pTUwOiZmjxM9n0xoLsGmr5lQ094"
    )

    // 🌱 SOIL ANALYSIS
    suspend fun analyzeSoil(prompt: String): String {

        return try {

            val response = generativeModel.generateContent(
                """
You are an agriculture soil expert.

Analyze the soil and return ONLY in this format:

SOIL_TYPE:
<soil type>

ANALYSIS:
<analysis>

CROPS:
<comma separated crops>

PROCESS:
<process>

Rules:
- Keep analysis short
- CROPS should be comma separated
- PROCESS should be practical and simple
- No markdown
- No extra symbols

Farmer Soil Data:
$prompt
                """.trimIndent()
            )

            response.text ?: "ERROR: Empty AI response"

        } catch (e: Exception) {

            "ERROR: ${e.message}"
        }
    }

    // 🤖 RAITHA ASSIST CHAT
    suspend fun getResponse(prompt: String): String {

        return try {

            val response = generativeModel.generateContent(
                """
You are Raitha Assist, an intelligent agriculture AI assistant for Indian farmers.

Rules:
- Give short practical answers
- Use simple farmer-friendly language
- Help with:
  • Crops
  • Soil
  • Irrigation
  • Fertilizers
  • Weather
  • Pest control
  • Organic farming

Farmer Question:
$prompt
                """.trimIndent()
            )

            response.text ?: "No AI response"

        } catch (e: Exception) {

            "Error: ${e.message}"
        }
    }
}