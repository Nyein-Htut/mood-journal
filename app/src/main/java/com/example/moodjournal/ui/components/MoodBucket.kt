package com.example.moodjournal.ui.components

import androidx.compose.ui.graphics.Color
import com.example.moodjournal.ui.theme.MoodAwful
import com.example.moodjournal.ui.theme.MoodBad
import com.example.moodjournal.ui.theme.MoodGood
import com.example.moodjournal.ui.theme.MoodGreat
import com.example.moodjournal.ui.theme.MoodNeutral

enum class MoodBucket(val label: String, val color: Color) {
    AWFUL("Awful", MoodAwful),
    BAD("Bad", MoodBad),
    NEUTRAL("Neutral", MoodNeutral),
    GOOD("Good", MoodGood),
    GREAT("Great", MoodGreat)
}

/** Maps the AI's -5..5 mood score onto the 5-level cute mood system used in the UI. */
fun moodBucketFor(score: Int?): MoodBucket? = when (score) {
    null -> null
    in Int.MIN_VALUE..-3 -> MoodBucket.AWFUL
    in -2..-1 -> MoodBucket.BAD
    0 -> MoodBucket.NEUTRAL
    in 1..2 -> MoodBucket.GOOD
    else -> MoodBucket.GREAT
}
