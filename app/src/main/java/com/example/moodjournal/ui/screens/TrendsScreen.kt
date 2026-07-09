package com.example.moodjournal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.components.MoodBucket
import com.example.moodjournal.ui.components.moodBucketFor
import com.example.moodjournal.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreen(
    entries: List<JournalEntry>,
    insight: String?,
    isLoadingInsight: Boolean,
    onBack: () -> Unit,
    onRequestInsight: () -> Unit
) {
    var selectedRange by remember { mutableStateOf("Week") }
    val isDarkMode = MaterialTheme.colorScheme.background == DarkPageBg1
    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    val filteredEntries = remember(entries, selectedRange) {
        val cutoff = when (selectedRange) {
            "Month" -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)
            "3M" -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(90)
            else -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        }
        entries.filter { it.timestamp >= cutoff }.sortedBy { it.timestamp }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Trends 📈", fontWeight = FontWeight.Bold, color = textPrimary) },
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
            TimeRangeSelector(selectedRange, onRangeSelected = { selectedRange = it }, isDarkMode)

            Spacer(Modifier.height(16.dp))
            MoodSwingsCard(filteredEntries, selectedRange, isDarkMode)
            
            Spacer(Modifier.height(16.dp))
            MoodMixChart(filteredEntries, isDarkMode)

            Spacer(Modifier.height(16.dp))
            GentleInsightSection(insight, isLoadingInsight, onRequestInsight, isDarkMode)

            Spacer(Modifier.height(16.dp))
            DailyRhythmSection(filteredEntries, isDarkMode)

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TimeRangeSelector(selected: String, onRangeSelected: (String) -> Unit, isDarkMode: Boolean) {
    val containerBg = if (isDarkMode) DarkStatTrack else LightStatTrack
    val activeColor = if (isDarkMode) DarkCoral else LightCoralDeep
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(containerBg, RoundedCornerShape(22.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val options = listOf("Week", "Month", "3M")
        options.forEach { opt ->
            val isActive = selected == opt
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (isActive) activeColor else Color.Transparent)
                    .clickable { onRangeSelected(opt) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    opt,
                    color = if (isActive) Color.White else (if (isDarkMode) DarkText2 else LightText2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun MoodSwingsCard(entries: List<JournalEntry>, range: String, isDarkMode: Boolean) {
    val dailyData = remember(entries) { buildDailyAverages(entries) }
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    val avgScore = if (dailyData.isEmpty()) 0f else dailyData.map { it.avgScore }.average().toFloat()
    val wellbeing = (((avgScore + 5f) * 10f).toInt()).coerceIn(0, 100)

    val peakDay = if (dailyData.isEmpty()) "-" else dailyData.maxBy { it.avgScore }.dayLabel
    
    // Simple trend calculation: compare first half with second half
    val trend = if (dailyData.size < 2) "0%" else {
        val mid = dailyData.size / 2
        val firstHalfAvg = dailyData.take(mid).map { it.avgScore }.average()
        val secondHalfAvg = dailyData.takeLast(dailyData.size - mid).map { it.avgScore }.average()
        val diff = secondHalfAvg - firstHalfAvg
        val percent = (diff / 10f * 100f).toInt()
        (if (percent >= 0) "+" else "") + "$percent%"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Mood this $range", fontWeight = FontWeight.Bold, color = textPrimary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(LightMintDeep, CircleShape))
                    Spacer(Modifier.width(6.dp))
                    Text("avg $wellbeing", color = textSecondary, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                if (dailyData.size >= 2) {
                    MoodLineChart(dailyData, isDarkMode)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Add more logs to see your journey! ✨", color = textSecondary, fontSize = 14.sp)
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallTrendStat("☀️", peakDay, "Peak day", isDarkMode, Modifier.weight(1f))
                SmallTrendStat("📊", "$wellbeing", "Avg score", isDarkMode, Modifier.weight(1f))
                SmallTrendStat(if (trend.startsWith("+")) "📈" else "📉", trend, "Trend", isDarkMode, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MoodLineChart(data: List<DailyMood>, isDarkMode: Boolean) {
    val chartColor = Color(0xFFFF6F91)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1).coerceAtLeast(1)
        
        val points = data.mapIndexed { index, day ->
            val x = index * spacing
            val y = height - ((day.avgScore + 5f) / 10f * height)
            Offset(x, y)
        }

        // Area under line
        val areaPath = Path().apply {
            moveTo(0f, height)
            if (points.isNotEmpty()) {
                lineTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    cubicTo(p0.x + (p1.x - p0.x) / 2f, p0.y, p0.x + (p1.x - p0.x) / 2f, p1.y, p1.x, p1.y)
                }
            }
            lineTo(width, height)
            close()
        }
        drawPath(
            areaPath,
            brush = Brush.verticalGradient(listOf(chartColor.copy(alpha = 0.2f), Color.Transparent))
        )

        // Line
        val linePath = Path().apply {
            if (points.isNotEmpty()) {
                moveTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    cubicTo(p0.x + (p1.x - p0.x) / 2f, p0.y, p0.x + (p1.x - p0.x) / 2f, p1.y, p1.x, p1.y)
                }
            }
        }
        drawPath(linePath, chartColor, style = Stroke(3.dp.toPx(), cap = StrokeCap.Round))

        // Dots
        points.forEach { point ->
            drawCircle(Color.White, 5.dp.toPx(), point)
            drawCircle(chartColor, 3.dp.toPx(), point, style = Stroke(2.dp.toPx()))
        }
    }
}

@Composable
private fun SmallTrendStat(emoji: String, value: String, label: String, isDarkMode: Boolean, modifier: Modifier) {
    val bg = if (isDarkMode) DarkStatTrack else LightStatTrack
    Column(
        modifier = modifier.background(bg, RoundedCornerShape(16.dp)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 16.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = if (isDarkMode) DarkText1 else LightText1, maxLines = 1)
        Text(label, fontSize = 10.sp, color = if (isDarkMode) DarkText3 else LightText3)
    }
}

@Composable
private fun MoodMixChart(entries: List<JournalEntry>, isDarkMode: Boolean) {
    val analyzed = entries.filter { it.moodScore != null }
    val totalCount = analyzed.size
    val counts = analyzed.groupingBy { moodBucketFor(it.moodScore)!! }.eachCount()
    
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    val avgScore = if (analyzed.isEmpty()) 0f else analyzed.map { it.moodScore!! }.average().toFloat()
    val wellbeing = ((avgScore + 5f) * 10f).toInt().coerceIn(0, 100)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mood mix 🧠", fontWeight = FontWeight.Bold, color = textPrimary)
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        var startAngle = -90f
                        if (totalCount == 0) {
                            drawArc(Color.LightGray.copy(alpha = 0.2f), 0f, 360f, false, style = Stroke(22.dp.toPx(), cap = StrokeCap.Round))
                        } else {
                            MoodBucket.entries.forEach { bucket ->
                                val sweep = (counts[bucket] ?: 0).toFloat() / totalCount * 360f
                                if (sweep > 0) {
                                    drawArc(bucket.color, startAngle, sweep, false, style = Stroke(22.dp.toPx(), cap = StrokeCap.Round))
                                    startAngle += sweep
                                }
                            }
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$wellbeing", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                        Text("mood score", fontSize = 9.sp, color = textSecondary)
                    }
                }
                Spacer(Modifier.width(32.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    MoodBucket.entries.reversed().forEach { bucket ->
                        val count = counts[bucket] ?: 0
                        val percent = if (totalCount == 0) 0 else (count * 100 / totalCount)
                        MoodMixLegendItem(bucket.label, "$percent%", bucket.color, isDarkMode)
                    }
                }
            }
        }
    }
}

@Composable
private fun MoodMixLegendItem(label: String, percent: String, color: Color, isDarkMode: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
        Spacer(Modifier.width(8.dp))
        Text(label, fontSize = 12.sp, color = if (isDarkMode) DarkText2 else LightText2, modifier = Modifier.weight(1f))
        Text(percent, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) DarkText1 else LightText1)
    }
}

@Composable
private fun GentleInsightSection(insight: String?, isLoadingInsight: Boolean, onRequestInsight: () -> Unit, isDarkMode: Boolean) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("💌", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text("Gentle insight", fontWeight = FontWeight.Bold, color = textPrimary)
            }
            Spacer(Modifier.height(16.dp))
            
            if (insight != null) {
                insight.split("\n").filter { it.isNotBlank() }.forEach { line ->
                    Text(
                        line.trim(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            } else {
                Text(
                    "Log more entries and tap below to generate a deep dive into your emotional patterns. ✨",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    lineHeight = 22.sp
                )
            }
            
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onRequestInsight,
                enabled = !isLoadingInsight,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightCoral)
            ) {
                if (isLoadingInsight) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color.White)
                    Spacer(Modifier.width(8.dp))
                }
                Text(if (insight == null) "Get an insight ✨" else "Refresh insight ✨", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DailyRhythmSection(entries: List<JournalEntry>, isDarkMode: Boolean) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    
    val rhythmData = remember(entries) { calculateRhythm(entries) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, if (isDarkMode) DarkCardBorder else LightCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Daily rhythm", fontWeight = FontWeight.Bold, color = textPrimary)
                Spacer(Modifier.width(8.dp))
                Text("🕙", fontSize = 16.sp)
            }
            Spacer(Modifier.height(20.dp))
            
            RhythmRow("🌅", "Morning", rhythmData["Morning"] ?: emptyList(), isDarkMode)
            RhythmRow("☀️", "Afternoon", rhythmData["Afternoon"] ?: emptyList(), isDarkMode)
            RhythmRow("🌙", "Evening", rhythmData["Evening"] ?: emptyList(), isDarkMode)
            
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text("low", fontSize = 10.sp, color = if (isDarkMode) DarkText3 else LightText3)
                Spacer(Modifier.width(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    repeat(5) { i ->
                        Box(modifier = Modifier.size(12.dp, 6.dp).background(LightCoral.copy(alpha = 0.2f * (i+1)), RoundedCornerShape(2.dp)))
                    }
                }
                Spacer(Modifier.width(4.dp))
                Text("high", fontSize = 10.sp, color = if (isDarkMode) DarkText3 else LightText3)
            }
        }
    }
}

private fun calculateRhythm(entries: List<JournalEntry>): Map<String, List<Int>> {
    val periods = mapOf(
        "Morning" to 5..11,
        "Afternoon" to 12..17,
        "Evening" to 18..23
    )
    val result = mutableMapOf<String, List<Int>>()
    
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_YEAR)
    
    periods.forEach { (name, range) ->
        val scores = IntArray(7) { 0 }
        val counts = IntArray(7) { 0 }
        
        entries.filter { it.moodScore != null }.forEach { entry ->
            calendar.timeInMillis = entry.timestamp
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val day = calendar.get(Calendar.DAY_OF_YEAR)
            val daysAgo = (today - day + 365) % 365
            
            if (hour in range && daysAgo < 7) {
                val score = ((entry.moodScore!! + 5) * 10)
                scores[6 - daysAgo] += score
                counts[6 - daysAgo] += 1
            }
        }
        
        result[name] = scores.mapIndexed { i, sum ->
            if (counts[i] == 0) 0 else sum / counts[i]
        }
    }
    return result
}

@Composable
private fun RhythmRow(emoji: String, label: String, scores: List<Int>, isDarkMode: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
        Text(emoji, fontSize = 16.sp)
        Spacer(Modifier.width(8.dp))
        Text(label, fontSize = 12.sp, color = if (isDarkMode) DarkText2 else LightText2, modifier = Modifier.width(70.dp))
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            val displayScores = if (scores.isEmpty()) List(7) { 0 } else scores
            displayScores.forEach { score ->
                Box(
                    modifier = Modifier
                        .size(32.dp, 24.dp)
                        .background(if (score > 0) LightCoral.copy(alpha = (score/100f).coerceIn(0.1f, 1f)) else Color.Gray.copy(alpha = 0.05f), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (score > 0) {
                        Text("$score", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

data class DailyMood(val dayLabel: String, val avgScore: Float, val timestamp: Long)

fun buildDailyAverages(entries: List<JournalEntry>): List<DailyMood> {
    val fmt = SimpleDateFormat("EEE", Locale.getDefault())
    val grouped = entries
        .filter { it.moodScore != null }
        .groupBy { entry ->
            val cal = Calendar.getInstance().apply {
                timeInMillis = entry.timestamp
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }
            cal.timeInMillis
        }
    return grouped.entries
        .sortedBy { it.key }
        .map { (dayMillis, dayEntries) ->
            val avg = dayEntries.mapNotNull { it.moodScore }.average().toFloat()
            DailyMood(fmt.format(Date(dayMillis)), avg, dayMillis)
        }
}
