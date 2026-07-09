package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.components.BlobCharacter
import com.example.moodjournal.ui.components.BlobSizes
import com.example.moodjournal.ui.components.MoodJar
import com.example.moodjournal.ui.components.moodBucketFor
import com.example.moodjournal.ui.theme.*
import com.example.moodjournal.util.MoodStat
import com.example.moodjournal.util.calculateMoodStability
import com.example.moodjournal.util.calculateStressLevel
import com.example.moodjournal.viewmodel.JournalViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalListScreen(
    entries: List<JournalEntry>,
    onAddClick: () -> Unit,
    onEntryClick: (JournalEntry) -> Unit,
    onTrendsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: JournalViewModel // Added to handle theme toggle
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showThemeSheet by remember { mutableStateOf(false) }

    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText2 else LightText2

    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    HeaderSection(
                        isDarkMode = isDarkMode,
                        onSettingsClick = { showThemeSheet = true },
                        onApiKeyClick = onSettingsClick
                    )
                }

                item {
                    ReflectButton(onAddClick, isDarkMode)
                }

                item {
                    MoodJarSection(entries, isDarkMode, onEntryClick, onTrendsClick)
                }

                item {
                    StatsSection(entries, isDarkMode)
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 22.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Emotion logs",
                            style = MaterialTheme.typography.titleMedium,
                            color = textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "TODAY",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isDarkMode) DarkCoral else LightCoralDeep,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                if (entries.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No logs yet.", color = textSecondary)
                        }
                    }
                } else {
                    items(entries.reversed().take(10)) { entry ->
                        EntryItem(entry, isDarkMode, onEntryClick)
                    }
                }
            }
        }
    }

    if (showThemeSheet) {
        ModalBottomSheet(
            onDismissRequest = { showThemeSheet = false },
            sheetState = sheetState,
            containerColor = if (isDarkMode) DarkSheetBg else LightSheetBg,
            dragHandle = {
                Box(
                    Modifier
                        .padding(vertical = 14.dp)
                        .width(36.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(if (isDarkMode) DarkStatTrack else LightStatTrack)
                )
            }
        ) {
            ThemeSelectionSheet(
                isDarkMode = isDarkMode,
                onThemeChange = { viewModel.setDarkMode(it) },
                onClose = { showThemeSheet = false }
            )
        }
    }
}

@Composable
private fun HeaderSection(
    isDarkMode: Boolean,
    onSettingsClick: () -> Unit,
    onApiKeyClick: () -> Unit
) {
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText2 else LightText2
    val glassBg = if (isDarkMode) DarkGlassBg else LightGlassBg
    val glassBorder = if (isDarkMode) DarkGlassBorder else LightGlassBorder

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                "Greetings, friend 🌸",
                color = textSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "How's your mood today?",
                style = MaterialTheme.typography.headlineMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GlassIconButton(Icons.Default.Settings, glassBg, glassBorder, textPrimary, onClick = onSettingsClick)
            GlassIconButton(Icons.Default.VpnKey, glassBg, glassBorder, textPrimary, onClick = onApiKeyClick)
        }
    }
}

@Composable
private fun GlassIconButton(
    icon: ImageVector,
    bg: Color,
    border: Color,
    tint: Color,
    showDot: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(bg)
            .clickable { onClick() }
            .padding(1.dp) // Simulation of border
            .background(border, CircleShape)
            .padding(1.dp)
            .background(bg, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = tint)
        if (showDot) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 9.dp)
                    .size(7.dp)
                    .background(LightCoralDeep, CircleShape)
            )
        }
    }
}

@Composable
private fun ReflectButton(onClick: () -> Unit, isDarkMode: Boolean) {
    val pillBg = if (isDarkMode) DarkPillBg else LightPillBg
    val pillText = if (isDarkMode) Color(0xFFFFF3F6) else Color.White

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(pillBg)
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Reflect your emotion",
                    fontSize = 16.sp,
                    color = pillText,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = pillText,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun MoodJarSection(
    entries: List<JournalEntry>,
    isDarkMode: Boolean,
    onEntryClick: (JournalEntry) -> Unit,
    onTrendsClick: () -> Unit
) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val cardBorder = if (isDarkMode) DarkCardBorder else LightCardBorder
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText2 else LightText2

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Your mood jar",
                    fontWeight = FontWeight.Bold,
                    color = textPrimary,
                    fontSize = 16.sp
                )
            }
            
            MoodJar(
                entries = entries,
                onBubbleClick = onEntryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
private fun StatsSection(entries: List<JournalEntry>, isDarkMode: Boolean) {
    val stability = remember(entries) { calculateMoodStability(entries) }
    val stress = remember(entries) { calculateStressLevel(entries) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "Mood stability",
            stat = stability,
            isDarkMode = isDarkMode,
            accentBrush = if (isDarkMode) {
                Brush.horizontalGradient(listOf(DarkMintDeep, DarkMint))
            } else {
                Brush.horizontalGradient(listOf(LightMintDeep, LightMint))
            },
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Stress level",
            stat = stress,
            isDarkMode = isDarkMode,
            accentBrush = if (isDarkMode) {
                Brush.horizontalGradient(listOf(DarkLavDeep, DarkLav))
            } else {
                Brush.horizontalGradient(listOf(LightLavDeep, LightLav))
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    stat: MoodStat,
    isDarkMode: Boolean,
    accentBrush: Brush,
    modifier: Modifier
) {
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val cardBorder = if (isDarkMode) DarkCardBorder else LightCardBorder
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText2 else LightText2
    val trackBg = if (isDarkMode) DarkStatTrack else LightStatTrack

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                fontSize = 12.5.sp
            )
            Text(
                stat.label,
                color = if (isDarkMode) DarkText3 else LightText3,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "${stat.score}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                fontSize = 30.sp
            )
            Spacer(Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(trackBg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = (stat.score / 100f).coerceIn(0.01f, 1f))
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(accentBrush)
                )
            }
        }
    }
}

@Composable
private fun EntryItem(entry: JournalEntry, isDarkMode: Boolean, onClick: (JournalEntry) -> Unit) {
    val dateFmt = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }
    val bucket = moodBucketFor(entry.moodScore)
    val cardBg = if (isDarkMode) DarkCardBg else LightCardBg
    val cardBorder = if (isDarkMode) DarkCardBorder else LightCardBorder
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .background(cardBg, RoundedCornerShape(20.dp))
            .clickable { onClick(entry) }
            .padding(1.dp) // Simulation of border
            .background(cardBorder, RoundedCornerShape(20.dp))
            .padding(1.dp)
            .background(cardBg, RoundedCornerShape(20.dp))
            .padding(12.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BlobCharacter(
            bucket = bucket,
            seed = entry.id.toInt(),
            modifier = Modifier.size(42.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                entry.text,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                fontSize = 14.sp
            )
            Text(
                dateFmt.format(Date(entry.timestamp)),
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = textSecondary
        )
    }
}

@Composable
private fun ThemeSelectionSheet(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val trackBg = if (isDarkMode) DarkStatTrack else LightStatTrack
    val cardBorder = if (isDarkMode) DarkCardBorder else LightCardBorder
    val textSecondary = if (isDarkMode) DarkText2 else LightText2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 10.dp)
    ) {
        Text(
            "Appearance",
            style = MaterialTheme.typography.titleMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 14.dp)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(trackBg, RoundedCornerShape(16.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ThemeOption(
                label = "Light",
                icon = Icons.Default.WbSunny,
                selected = !isDarkMode,
                onClick = { onThemeChange(false) },
                modifier = Modifier.weight(1f)
            )
            ThemeOption(
                label = "Dark",
                icon = Icons.Default.NightlightRound,
                selected = isDarkMode,
                onClick = { onThemeChange(true) },
                modifier = Modifier.weight(1f)
            )
        }
        
        Button(
            onClick = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 26.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
        ) {
            Text("Done", color = textSecondary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ThemeOption(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pillBg = if (selected) {
        if (label == "Dark") DarkPillBg else LightPillBg
    } else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    
    val contentColor = if (selected) Color.White else LightText2

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(pillBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(15.dp), tint = contentColor)
            Text(label, color = contentColor, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
        }
    }
}
