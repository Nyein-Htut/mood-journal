package com.example.moodjournal.util

import com.example.moodjournal.data.AnalysisStatus
import com.example.moodjournal.data.JournalEntry
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

/** A single computed stat card: a 0-100 score plus a friendly label to show under the title. */
data class MoodStat(val score: Int, val label: String)

private const val WINDOW_DAYS = 14L
private const val WINDOW_MS = WINDOW_DAYS * 24L * 60L * 60L * 1000L

private fun analyzedWindow(entries: List<JournalEntry>): List<JournalEntry> {
    val cutoff = System.currentTimeMillis() - WINDOW_MS
    return entries.filter { it.status == AnalysisStatus.DONE && it.moodScore != null && it.timestamp >= cutoff }
}

/**
 * Mood stability: how consistent the mood score has been over the last two weeks.
 * Computed from the standard deviation of daily mood scores (range -5..5) - a calm,
 * even week scores high; a week of big swings scores low.
 */
fun calculateMoodStability(entries: List<JournalEntry>): MoodStat {
    val recent = analyzedWindow(entries)
    if (recent.size < 2) return MoodStat(70, "Just getting started")

    val scores = recent.mapNotNull { it.moodScore?.toDouble() }
    val mean = scores.average()
    val variance = scores.sumOf { (it - mean) * (it - mean) } / scores.size
    val stdDev = sqrt(variance)

    // A stdDev of 0 (perfectly even) -> 100. A stdDev of 5 (max possible swing) -> ~0.
    val score = (100 - (stdDev / 5.0) * 100).roundToInt().coerceIn(0, 100)

    val label = when {
        score >= 80 -> "Very steady"
        score >= 60 -> "Balanced"
        score >= 40 -> "Shifting"
        score >= 20 -> "Rocky"
        else -> "Turbulent"
    }
    return MoodStat(score, label)
}

/**
 * Stress level: a blend of how often recent entries land in the low moods, how intense
 * those low moods are, and how often the on-device concern check has fired.
 */
fun calculateStressLevel(entries: List<JournalEntry>): MoodStat {
    val recent = analyzedWindow(entries)
    if (recent.isEmpty()) return MoodStat(0, "No data yet")

    val total = recent.size
    val negativeEntries = recent.filter { (it.moodScore ?: 0) < 0 }
    val negativeFraction = negativeEntries.size.toDouble() / total

    val avgNegativeIntensity = if (negativeEntries.isNotEmpty()) {
        negativeEntries.mapNotNull { it.moodScore }.map { abs(it) }.average() / 5.0
    } else 0.0

    val concernFraction = recent.count { it.concernFlag }.toDouble() / total

    val rawScore = (negativeFraction * 40) + (avgNegativeIntensity * 30) + (concernFraction * 30)
    val score = rawScore.coerceIn(0.0, 100.0).roundToInt()

    val label = when {
        score >= 75 -> "High"
        score >= 50 -> "Elevated"
        score >= 25 -> "Mild"
        else -> "Low"
    }
    return MoodStat(score, label)
}
