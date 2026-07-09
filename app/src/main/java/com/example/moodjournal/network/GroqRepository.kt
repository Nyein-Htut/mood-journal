package com.example.moodjournal.network

import com.example.moodjournal.data.JournalEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Talks to Groq's OpenAI-compatible chat completions endpoint.
 * https://console.groq.com/docs/api-reference
 *
 * NOTE: Groq periodically retires/renames models. If GROQ_MODEL below
 * ever returns a "model_decommissioned" error, check console.groq.com
 * for the current recommended model name and swap it in.
 */
class GroqRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json".toMediaType()

    companion object {
        private const val ENDPOINT = "https://api.groq.com/openai/v1/chat/completions"
        private const val GROQ_MODEL = "llama-3.3-70b-versatile"
    }

    suspend fun analyzeEntry(text: String, apiKey: String): GroqResult<EntryAnalysis> =
        withContext(Dispatchers.IO) {
            val systemPrompt = """
                You are a careful, non-clinical journaling assistant. You analyze a single
                diary entry and return ONLY a JSON object, no prose, no markdown fences.

                Schema:
                {
                  "mood_score": <integer from -5 (very negative) to 5 (very positive)>,
                  "primary_emotion": <single word, e.g. "anxious", "content", "frustrated">,
                  "themes": [<1 to 4 short lowercase theme tags, e.g. "work", "sleep", "family">],
                  "concern_flag": <true if the text suggests acute distress, hopelessness,
                                    or self-harm risk, otherwise false>,
                  "reflection": <one short, warm, non-diagnostic sentence. Never diagnose a
                                   condition. Never give medical advice. If concern_flag is
                                   true, gently encourage reaching out to a trusted person
                                   or professional instead of offering a fix.>
                }
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("model", GROQ_MODEL)
                put("temperature", 0.3)
                put("response_format", JSONObject().put("type", "json_object"))
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemPrompt)
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", text)
                    })
                })
            }

            try {
                val request = Request.Builder()
                    .url(ENDPOINT)
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(requestJson.toString().toRequestBody(jsonMediaType))
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (!response.isSuccessful) {
                        return@withContext GroqResult.Failure(
                            "Groq API error (${response.code}): ${bodyStr.take(200)}"
                        )
                    }

                    val content = JSONObject(bodyStr)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                    val parsed = JSONObject(content)
                    val themes = mutableListOf<String>()
                    parsed.optJSONArray("themes")?.let { arr ->
                        for (i in 0 until arr.length()) themes.add(arr.getString(i))
                    }

                    GroqResult.Success(
                        EntryAnalysis(
                            moodScore = parsed.optInt("mood_score", 0).coerceIn(-5, 5),
                            primaryEmotion = parsed.optString("primary_emotion", "neutral"),
                            themes = themes,
                            concernFlag = parsed.optBoolean("concern_flag", false),
                            reflection = parsed.optString("reflection", "")
                        )
                    )
                }
            } catch (e: Exception) {
                GroqResult.Failure(e.message ?: "Unknown network error")
            }
        }

    /** Produces short, supportive, bulleted insights based on recent trends. */
    suspend fun getTrendInsight(entries: List<JournalEntry>, apiKey: String): GroqResult<String> =
        withContext(Dispatchers.IO) {
            if (entries.isEmpty()) return@withContext GroqResult.Failure("No entries to analyze yet")

            val summary = entries.joinToString("\n") { e ->
                "score=${e.moodScore ?: "?"} emotion=${e.primaryEmotion ?: "?"} themes=${e.themes ?: ""}"
            }

            val systemPrompt = """
                You are a supportive, non-clinical wellbeing assistant. You will see a list
                of recent journal-entry summaries.
                Write 3-4 short, supportive bullet points (one per line). 
                Start each line with a unique, relevant emoji.
                Focus on patterns and gentle suggestions.
                Do NOT diagnose anything. Be warm and plain-spoken.
                Return plain text only, no JSON, no markdown.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("model", GROQ_MODEL)
                put("temperature", 0.5)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemPrompt)
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", summary)
                    })
                })
            }

            try {
                val request = Request.Builder()
                    .url(ENDPOINT)
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(requestJson.toString().toRequestBody(jsonMediaType))
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (!response.isSuccessful) {
                        return@withContext GroqResult.Failure(
                            "Groq API error (${response.code}): ${bodyStr.take(200)}"
                        )
                    }
                    val content = JSONObject(bodyStr)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    GroqResult.Success(content.trim())
                }
            } catch (e: Exception) {
                GroqResult.Failure(e.message ?: "Unknown network error")
            }
        }

    suspend fun getWeeklyVibe(entries: List<JournalEntry>, apiKey: String): GroqResult<String> =
        withContext(Dispatchers.IO) {
            if (entries.isEmpty()) return@withContext GroqResult.Success("Ready to start your journey? ✨")
            
            val summary = entries.takeLast(10).joinToString(", ") { it.primaryEmotion ?: "neutral" }
            
            val systemPrompt = """
                Based on these recent emotions: $summary.
                Generate a short "Weather-like" vibe summary (e.g., "Mostly sunny with patches of calm").
                Include one weather emoji at the end. Max 8 words. Plain text only.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("model", GROQ_MODEL)
                put("temperature", 0.6)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemPrompt)
                    })
                })
            }

            try {
                val request = Request.Builder()
                    .url(ENDPOINT)
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(requestJson.toString().toRequestBody(jsonMediaType))
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (!response.isSuccessful) return@withContext GroqResult.Success("Sunny with calm patches ☀️")
                    val content = JSONObject(bodyStr)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    GroqResult.Success(content.trim())
                }
            } catch (e: Exception) {
                GroqResult.Success("Sunny with calm patches ☀️")
            }
        }

    suspend fun getDailyAffirmation(apiKey: String): GroqResult<String> =
        withContext(Dispatchers.IO) {
            val systemPrompt = """
                You are a supportive assistant. Generate ONE short, warm, and uplifting
                daily affirmation for someone journaling their moods. Include 1-2 cute
                emojis. Keep it under 15 words. Return plain text only.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("model", GROQ_MODEL)
                put("temperature", 0.7)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemPrompt)
                    })
                })
            }

            try {
                val request = Request.Builder()
                    .url(ENDPOINT)
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(requestJson.toString().toRequestBody(jsonMediaType))
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (!response.isSuccessful) return@withContext GroqResult.Failure("API Error")
                    val content = JSONObject(bodyStr)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    GroqResult.Success(content.trim())
                }
            } catch (e: Exception) {
                GroqResult.Failure("Error")
            }
        }
}
