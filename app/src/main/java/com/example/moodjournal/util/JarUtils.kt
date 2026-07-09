package com.example.moodjournal.util

import com.example.moodjournal.data.JournalEntry

/** How many mood bubbles fit in one jar before a new jar starts. */
const val JAR_CAPACITY = 12

data class JarInfo(
    val jarNumber: Int,
    val entries: List<JournalEntry>,
    val isCurrent: Boolean
)

/** Buckets all analyzed entries into numbered jars of JAR_CAPACITY each, oldest jar first. */
fun bucketIntoJars(entries: List<JournalEntry>): List<JarInfo> {
    val analyzed = entries.filter { it.moodScore != null }.sortedBy { it.timestamp }
    if (analyzed.isEmpty()) return emptyList()

    val grouped = analyzed
        .mapIndexed { index, entry -> (index / JAR_CAPACITY) to entry }
        .groupBy({ it.first }, { it.second })

    val maxJarIndex = grouped.keys.max()
    return grouped.entries
        .sortedBy { it.key }
        .map { (idx, list) -> JarInfo(jarNumber = idx + 1, entries = list, isCurrent = idx == maxJarIndex) }
}

/** The jar currently being filled (shown on the home screen). */
fun currentJarEntries(entries: List<JournalEntry>): List<JournalEntry> =
    bucketIntoJars(entries).lastOrNull()?.entries ?: emptyList()

fun currentJarNumber(entries: List<JournalEntry>): Int =
    bucketIntoJars(entries).lastOrNull()?.jarNumber ?: 1
