// app/src/main/java/com/example/moodjournal/ui/theme/EntryBackgrounds.kt
package com.example.moodjournal.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class EntryBackground(val id: String, val label: String, val brush: Brush)

val EntryBackgrounds = listOf(
    EntryBackground("clear", "Clear", Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent))),
    EntryBackground("blush", "Blush", Brush.verticalGradient(listOf(LightPink1, Color(0xFFFFF6F1)))),
    EntryBackground("sky", "Sky", Brush.verticalGradient(listOf(Color(0xFFDCEEFF), Color(0xFFF3FAFF)))),
    EntryBackground("lavender", "Lavender", Brush.verticalGradient(listOf(Color(0xFFEDE7FF), Color(0xFFF8F6FF)))),
    EntryBackground("mint", "Mint", Brush.verticalGradient(listOf(Color(0xFFE2FBF3), Color(0xFFF3FFFB)))),
    EntryBackground("peach", "Peach", Brush.verticalGradient(listOf(Color(0xFFFFEEDD), Color(0xFFFFF8F0)))),
    EntryBackground("sunset", "Sunset", Brush.linearGradient(listOf(Color(0xFFFFC9D8), Color(0xFFB8A6FF)))),
    EntryBackground("bloom", "Bloom", Brush.linearGradient(listOf(Color(0xFFFF9EBB), Color(0xFFFFD3AB))))
)

fun entryBackgroundFor(id: String?): EntryBackground =
    EntryBackgrounds.find { it.id == id } ?: EntryBackgrounds.first()

val AvailableStickers = listOf("🌸", "✨", "💫", "🌙", "☀️", "💌", "🎀", "🦋", "🌈", "💕", "🍓", "☁️", "🌿", "⭐", "🍒", "🌷")
