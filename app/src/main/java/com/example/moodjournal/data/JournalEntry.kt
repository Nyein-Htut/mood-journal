package com.example.moodjournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AnalysisStatus { PENDING, DONE, ERROR }

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val timestamp: Long,
    val moodScore: Int? = null,          // -5 (very low) .. 5 (very positive)
    val primaryEmotion: String? = null,
    val themes: String? = null,          // comma-separated theme tags
    val reflection: String? = null,      // short supportive, non-clinical note from the model
    val concernFlag: Boolean = false,    // true if language suggested acute distress
    val status: AnalysisStatus = AnalysisStatus.PENDING
)
