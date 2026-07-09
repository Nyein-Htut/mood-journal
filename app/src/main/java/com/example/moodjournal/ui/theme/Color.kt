package com.example.moodjournal.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ---- HTML Design Inspired Colors ----

// Light Theme
val LightPink1 = Color(0xFFFFE8EE)
val LightPink2 = Color(0xFFFFD3DF)
val LightPink3 = Color(0xFFFFC0CE)
val LightCoral = Color(0xFFFF6F91)
val LightCoralDeep = Color(0xFFE14D74)
val LightLav = Color(0xB8A6FFFF) // Note: HTML was #B8A6FF, adding FF for alpha
val LightLavDeep = Color(0xFF8B76E8)
val LightMint = Color(0xFF7FD8BE)
val LightMintDeep = Color(0xFF4CB596)
val LightPlum = Color(0xFF4A2E3D)
val LightPlumSoft = Color(0xFF8A6575)
val LightText1 = LightPlum
val LightText2 = LightPlumSoft
val LightText3 = Color(0xFFB79AA8)
val LightGlassBg = Color(0x8CFFFFFF) // 0.55 opacity
val LightGlassBorder = Color(0xBFFFFFFF) // 0.75 opacity
val LightPageBg1 = LightPink1
val LightPageBg2 = Color(0xFFFFF6F1)
val LightCardBg = Color(0x9EFFFFFF) // 0.62 opacity
val LightCardBorder = Color(0xCCFFFFFF) // 0.8 opacity
val LightStatTrack = Color(0x8CFFFFFF)
val LightSheetBg = Color(0xFFFFFDFB)

// Dark Theme
val DarkPageBg1 = Color(0xFF241722)
val DarkPageBg2 = Color(0xFF2E1B2C)
val DarkGlassBg = Color(0x8C3A2436) // 0.55 opacity
val DarkGlassBorder = Color(0x14FFFFFF) // 0.08 opacity
val DarkText1 = Color(0xFFFBEAF0)
val DarkText2 = Color(0xFFD6AFC0)
val DarkText3 = Color(0xFF8E6E7E)
val DarkCardBg = Color(0xA6321F2F) // 0.65 opacity
val DarkCardBorder = Color(0x0FFFFFFF) // 0.06 opacity
val DarkCoral = Color(0xFFFF89A6)
val DarkCoralDeep = Color(0xFFFF5C86)
val DarkLav = Color(0xFFC9B8FF)
val DarkLavDeep = Color(0xFFA78EFF)
val DarkMint = Color(0xFF8FE6CC)
val DarkMintDeep = Color(0xFF5FCBA9)
val DarkStatTrack = Color(0x14FFFFFF) // 0.08 opacity
val DarkSheetBg = Color(0xFF2E1B2C)

// Gradients
val LightPillBg = Brush.linearGradient(listOf(LightCoral, LightCoralDeep))
val DarkPillBg = Brush.linearGradient(listOf(Color(0xFFFF6F91), Color(0xFFB84AA0)))

val LightPageGradient = Brush.verticalGradient(listOf(LightPageBg1, LightPageBg2))
val DarkPageGradient = Brush.verticalGradient(listOf(DarkPageBg1, DarkPageBg2))

// Mood Buckets (Keep existing or update to match HTML feel)
val MoodAwful = Color(0xFF9B6FD1)
val MoodBad = Color(0xFF6FA3E0)
val MoodNeutral = Color(0xFF5AC8C0)
val MoodGood = Color(0xFF7ED67A)
val MoodGreat = Color(0xFFFFA552)

val MoodAwfulSoft = Color(0xFFE3D4F7)
val MoodBadSoft = Color(0xFFD8E9FA)
val MoodNeutralSoft = Color(0xFFD4F3EF)
val MoodGoodSoft = Color(0xFFDDF5DB)
val MoodGreatSoft = Color(0xFFFFE7D0)

// Legacy compatibility (if needed)
val PrimaryLavender = LightLavDeep
val AccentPeach = Color(0xFFFFB37B)
val AccentBubblegum = LightPink3
val TextInk = LightPlum
val TextMuted = LightText3
val SoftOutline = LightCardBorder
val CuteAppBackground = LightPageGradient
