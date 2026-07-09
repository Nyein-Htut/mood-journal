// app/src/main/java/com/example/moodjournal/data/JournalEntry.kt
package com.example.moodjournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AnalysisStatus { PENDING, DONE, ERROR }

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val timestamp: Long,
    val moodScore: Int? = null,
    val primaryEmotion: String? = null,
    val themes: String? = null,
    val reflection: String? = null,
    val concernFlag: Boolean = false,
    val status: AnalysisStatus = AnalysisStatus.PENDING,
    val backgroundId: String? = null,   // selected cute background swatch
    val stickers: String? = null        // comma-separated emoji, e.g. "🌸,✨"
)
