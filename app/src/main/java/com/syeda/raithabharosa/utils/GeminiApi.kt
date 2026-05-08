package com.syeda.raithabharosa.utils

import android.os.Handler
import android.os.Looper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object GeminiApi {

    // 🔑 Note: Ensure this key is valid and has "Generative Language API" enabled in Google AI Studio
    private const val API_KEY = "AIzaSyAq7B_Siws8ZCqqAix_HADq5zkL5CngDz4"

    private val client = OkHttpClient()

    fun getResponse(prompt: String, callback: (String) -> Unit) {
        val mainHandler = Handler(Looper.getMainLooper())

        try {
            // 1. Build the JSON structure exactly as required by the API
            val json = JSONObject()
            val content = JSONObject()
            val partsArray = JSONArray()
            val part = JSONObject()

            part.put("text", "You are an expert agriculture assistant. User question: $prompt")
            partsArray.put(part)
            content.put("parts", partsArray)
            
            val contentsArray = JSONArray()
            contentsArray.put(content)
            json.put("contents", contentsArray)

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                json.toString()
            )

            // 2. Switched to v1 (Stable) and ensured the URL format is correct
            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=$API_KEY")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    mainHandler.post { callback("Network Error: ${e.message}") }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string() ?: ""

                    if (!response.isSuccessful) {
                        mainHandler.post {
                            callback("API Error (${response.code}): Check if Gemini 1.5 is enabled in your AI Studio project.")
                        }
                        return
                    }

                    try {
                        val jsonObject = JSONObject(responseBody)
                        val candidates = jsonObject.optJSONArray("candidates")

                        if (candidates != null && candidates.length() > 0) {
                            val firstCandidate = candidates.getJSONObject(0)
                            val contentObj = firstCandidate.optJSONObject("content")
                            val partsJson = contentObj?.optJSONArray("parts")

                            if (partsJson != null && partsJson.length() > 0) {
                                val text = partsJson.getJSONObject(0).optString("text")
                                mainHandler.post { callback(text) }
                            } else {
                                mainHandler.post { callback("AI response was empty or blocked.") }
                            }
                        } else {
                            mainHandler.post { callback("No response from AI.") }
                        }
                    } catch (e: Exception) {
                        mainHandler.post { callback("Error reading AI response.") }
                    }
                }
            })
        } catch (e: Exception) {
            mainHandler.post { callback("Request Error: ${e.message}") }
        }
    }
}
