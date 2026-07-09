package com.example.moodjournal.network

data class EntryAnalysis(
    val moodScore: Int,
    val primaryEmotion: String,
    val themes: List<String>,
    val concernFlag: Boolean,
    val reflection: String
)

sealed class GroqResult<out T> {
    data class Success<T>(val data: T) : GroqResult<T>()
    data class Failure(val message: String) : GroqResult<Nothing>()
}
