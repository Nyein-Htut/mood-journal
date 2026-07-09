package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.components.moodBucketFor
import com.example.moodjournal.ui.theme.*
import com.example.moodjournal.viewmodel.JournalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    viewModel: JournalViewModel,
    onBack: () -> Unit
) {
    val entries by viewModel.entries.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val wellbeing by viewModel.wellbeingScore.collectAsState()
    val affirmation by viewModel.dailyAffirmation.collectAsState()
    val weeklyVibe by viewModel.weeklyVibe.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Insights 💡", fontWeight = FontWeight.Bold, color = textPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Your emotional story this week", color = textSecondary, fontSize = 13.sp, modifier = Modifier.padding(bottom = 12.dp))

            WeeklyVibeBanner(weeklyVibe, entries.size, streak, isDarkMode)

            Spacer(Modifier.height(16.dp))
            TopEmotionsSection(entries, isDarkMode)

            Spacer(Modifier.height(16.dp))
            WhatMovesYourMood(entries, isDarkMode)

            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallStatCard("🔥", "$streak", "day streak", "Keep it up! 🌟", isDarkMode, Modifier.weight(1f))
                SmallStatCard("💚", "$wellbeing", "wellbeing", if (wellbeing > 70) "Doing great! ✨" else "Room to grow 🌱", isDarkMode, Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))
            AchievementsSection(streak, entries.size, isDarkMode)

            Spacer(Modifier.height(16.dp))
            AffirmationCard(affirmation ?: "Every emotion you feel is a part of your story.", isDarkMode)

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun WeeklyVibeBanner(vibe: String, count: Int, streak: Int, isDarkMode: Boolean) {
    val bannerBrush = if (isDarkMode) {
        Brush.linearGradient(listOf(Color(0xFF8B76E8), Color(0xFFB84AA0)))
    } else {
        Brush.linearGradient(listOf(Color(0xFFB8A6FF), Color(0xFFFF6F91)))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(bannerBrush).padding(20.dp)) {
            Column {
                Text("THIS WEEK'S VIBE ✨", color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text(vibe, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp)
                Spacer(Modifier.height(8.dp))
                Text("$count emotions logged · $streak-day streak 🔥", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun TopEmotionsSection(entries: List<JournalEntry>, isDarkMode: Boolean) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1

    val lastWeek = System.currentTimeMillis() - java.util.concurrent.TimeUnit.DAYS.toMillis(7)
    val recent = entries.filter { it.timestamp >= lastWeek && it.primaryEmotion != null }
    val counts = recent.groupingBy { it.primaryEmotion!! }.eachCount()
    val total = counts.values.sum().toFloat()
    
    val topEmotions = counts.entries.sortedByDescending { it.value }.take(4)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Top emotions 💘", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(16.dp))
            
            if (topEmotions.isEmpty()) {
                Text("Log more emotions to see your trends! ✨", color = if (isDarkMode) DarkText3 else LightText3, fontSize = 14.sp)
            } else {
                topEmotions.forEach { (emotion, count) ->
                    val emoji = when (emotion.lowercase()) {
                        "happy", "content", "joy", "excited" -> "😊"
                        "calm", "peaceful", "relaxed" -> "😌"
                        "anxious", "worried", "stressed" -> "😰"
                        "sad", "lonely", "hopeless" -> "😔"
                        "angry", "frustrated", "annoyed" -> "😠"
                        else -> "😶"
                    }
                    EmotionRow(emoji, emotion.replaceFirstChar { it.uppercase() }, count / total, "$count", isDarkMode)
                }
            }
        }
    }
}

@Composable
private fun EmotionRow(emoji: String, label: String, progress: Float, count: String, isDarkMode: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
        Text(emoji, fontSize = 20.sp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) DarkText1 else LightText1)
                Text(count, fontSize = 12.sp, color = if (isDarkMode) DarkText3 else LightText3)
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                color = if (isDarkMode) DarkCoral else LightCoralDeep,
                trackColor = if (isDarkMode) DarkStatTrack else LightStatTrack
            )
        }
    }
}

@Composable
private fun WhatMovesYourMood(entries: List<JournalEntry>, isDarkMode: Boolean) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    val themeImpacts = remember(entries) {
        val impacts = mutableMapOf<String, MutableList<Int>>()
        entries.filter { it.moodScore != null && !it.themes.isNullOrBlank() }.forEach { entry ->
            entry.themes!!.split(",").forEach { theme ->
                impacts.getOrPut(theme.trim().lowercase()) { mutableListOf() }.add(entry.moodScore!!)
            }
        }
        impacts.mapValues { (_, scores) -> scores.average().toInt() }.entries
            .sortedByDescending { it.value.coerceAtLeast(0) - it.value.coerceAtMost(0) }
            .take(6)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("What moves your mood 🔍", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Based on your theme tags", color = textSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(16.dp))
            
            if (themeImpacts.isEmpty()) {
                Text("Add themes to your logs to see what moves your mood! 🏷️", color = textSecondary, fontSize = 14.sp)
            } else {
                FlowRow(modifier = Modifier.fillMaxWidth(), mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    themeImpacts.forEach { (theme, score) ->
                        val impact = if (score >= 0) "+$score" else "$score"
                        val color = if (score >= 0) LightMint else LightPink3
                        val emoji = when (theme) {
                            "sleep" -> "💤 "
                            "exercise", "workout", "run" -> "🏃‍♂️ "
                            "social", "friends", "family" -> "🤝 "
                            "work", "study" -> "💼 "
                            "screen time", "phone" -> "📱 "
                            "caffeine", "coffee" -> "☕ "
                            "nature", "walk" -> "🌲 "
                            "hobby", "art", "music" -> "🎨 "
                            else -> "✨ "
                        }
                        MoodTag(emoji + theme.replaceFirstChar { it.uppercase() }, impact, color, isDarkMode)
                    }
                }
            }
        }
    }
}

@Composable
private fun MoodTag(label: String, impact: String, color: Color, isDarkMode: Boolean) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.15f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) DarkText1 else LightText1)
            Spacer(Modifier.width(6.dp))
            Text(impact, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (impact.startsWith("+")) LightMintDeep else LightCoralDeep)
        }
    }
}

@Composable
private fun SmallStatCard(emoji: String, value: String, label: String, subtext: String, isDarkMode: Boolean, modifier: Modifier) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = textPrimary)
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textSecondary)
            Spacer(Modifier.height(4.dp))
            Text(subtext, fontSize = 10.sp, color = if (isDarkMode) DarkMintDeep else LightMintDeep, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AchievementsSection(streak: Int, totalEntries: Int, isDarkMode: Boolean) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Achievements 🏅", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(16.dp))
            
            AchievementRow("🌸", "7-day streak", "Logged every day this week", streak >= 7, isDarkMode)
            AchievementRow("☀️", "First reflection", "You opened up today", totalEntries > 0, isDarkMode)
            AchievementRow("🌈", "Mood rainbow", "Log 5 different emotions", totalEntries >= 5, isDarkMode)
            AchievementRow("💎", "30-day journey", "Stay consistent for a month", streak >= 30, isDarkMode)
        }
    }
}

@Composable
private fun AchievementRow(emoji: String, title: String, desc: String, completed: Boolean, isDarkMode: Boolean) {
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(40.dp).background(if (completed) LightPink2.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (completed) textPrimary else textSecondary)
            Text(desc, fontSize = 11.sp, color = textSecondary)
        }
        if (completed) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = LightCoralDeep, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun AffirmationCard(text: String, isDarkMode: Boolean) {
    val cardBrush = if (isDarkMode) {
        Brush.linearGradient(listOf(DarkPageBg2, Color(0xFF3D2A3A)))
    } else {
        Brush.linearGradient(listOf(LightPink1, LightPageBg2))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Box(modifier = Modifier.background(cardBrush).padding(24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🌸", fontSize = 24.sp)
                Spacer(Modifier.height(12.dp))
                Text(
                    "\"$text\"",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = if (isDarkMode) DarkText1 else LightText1,
                    lineHeight = 24.sp
                )
                Spacer(Modifier.height(8.dp))
                Text("Inner Weather, daily affirmation", fontSize = 11.sp, color = if (isDarkMode) DarkText3 else LightText3)
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.layout.Layout(content, modifier) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0

        placeables.forEach { placeable ->
            if (currentRowWidth + placeable.width + mainAxisSpacing.roundToPx() > constraints.maxWidth && currentRow.isNotEmpty()) {
                rows.add(currentRow)
                currentRow = mutableListOf()
                currentRowWidth = 0
            }
            currentRow.add(placeable)
            currentRowWidth += placeable.width + mainAxisSpacing.roundToPx()
        }
        if (currentRow.isNotEmpty()) rows.add(currentRow)

        val height = rows.sumOf { row -> row.maxOf { it.height } } + (rows.size - 1).coerceAtLeast(0) * crossAxisSpacing.roundToPx()
        val width = constraints.maxWidth

        layout(width, height) {
            var y = 0
            rows.forEach { row ->
                var x = 0
                val rowHeight = row.maxOf { it.height }
                row.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + mainAxisSpacing.roundToPx()
                }
                y += rowHeight + crossAxisSpacing.roundToPx()
            }
        }
    }
}
