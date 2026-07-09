package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.components.MoodJar
import com.example.moodjournal.ui.components.moodBucketFor
import com.example.moodjournal.ui.theme.*
import com.example.moodjournal.util.JarInfo
import com.example.moodjournal.util.bucketIntoJars
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JarHistoryScreen(
    entries: List<JournalEntry>,
    onBack: () -> Unit,
    onJarClick: (Int) -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkPageBg1
    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    val jars = remember(entries) { bucketIntoJars(entries).sortedByDescending { it.jarNumber } }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Your jars 🫙", fontWeight = FontWeight.Bold, color = textPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
        ) {
            if (jars.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No jars yet — start logging! ✨", color = textSecondary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    items(jars) { jar ->
                        JarSummaryCard(jar, isDarkMode, onClick = { onJarClick(jar.jarNumber) })
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun JarSummaryCard(jar: JarInfo, isDarkMode: Boolean, onClick: () -> Unit) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val cardBorder = if (isDarkMode) DarkCardBorder else LightCardBorder
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3
    val dateFmt = remember { SimpleDateFormat("MMM d", Locale.getDefault()) }

    val avgScore = jar.entries.mapNotNull { it.moodScore }.average().toFloat()
    val bucket = moodBucketFor(avgScore.toInt())
    val first = jar.entries.minByOrNull { it.timestamp }
    val last = jar.entries.maxByOrNull { it.timestamp }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(bucket?.color?.copy(alpha = 0.25f) ?: Color.Gray.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("${jar.jarNumber}", fontWeight = FontWeight.Bold, color = textPrimary)
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Jar #${jar.jarNumber}", fontWeight = FontWeight.Bold, color = textPrimary, fontSize = 15.sp)
                    if (jar.isCurrent) {
                        Spacer(Modifier.width(8.dp))
                        Text("· current", fontSize = 11.sp, color = textSecondary, fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    "${jar.entries.size} entries" +
                        if (first != null && last != null) " · ${dateFmt.format(Date(first.timestamp))}–${dateFmt.format(Date(last.timestamp))}" else "",
                    fontSize = 12.sp,
                    color = textSecondary
                )
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = textSecondary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JarDetailScreen(
    jarNumber: Int,
    entries: List<JournalEntry>,
    onBack: () -> Unit,
    onEntryClick: (JournalEntry) -> Unit
) {
    val isDarkMode = MaterialTheme.colorScheme.background == DarkPageBg1
    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground

    val jar = remember(entries, jarNumber) {
        bucketIntoJars(entries).find { it.jarNumber == jarNumber }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Jar #$jarNumber", fontWeight = FontWeight.Bold, color = textPrimary) },
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
                .padding(20.dp)
        ) {
            if (jar == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Jar not found.", color = textPrimary)
                }
            } else {
                Text(
                    "${jar.entries.size} moods captured in this jar",
                    color = textPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                MoodJar(
                    entries = jar.entries,
                    onBubbleClick = onEntryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
