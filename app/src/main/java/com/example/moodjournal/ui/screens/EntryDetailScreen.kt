// app/src/main/java/com/example/moodjournal/ui/screens/EntryDetailScreen.kt
package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.data.AnalysisStatus
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.components.BlobCharacter
import com.example.moodjournal.ui.components.BlobSizes
import com.example.moodjournal.ui.components.moodBucketFor
import com.example.moodjournal.ui.theme.*
import com.example.moodjournal.util.CrisisSupport
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(
    entry: JournalEntry,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onRetry: () -> Unit,
    onEditSave: (String) -> Unit
) {
    val dateFmt = SimpleDateFormat("EEEE, MMM d · h:mm a", Locale.getDefault())
    val backgroundBrush = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkText3 else LightText3

    var isEditing by remember(entry.id) { mutableStateOf(false) }
    var draftText by remember(entry.id) { mutableStateOf(entry.text) }

    val entryBg = entryBackgroundFor(entry.backgroundId)
    val stickerList = entry.stickers?.split(",")?.filter { it.isNotBlank() } ?: emptyList()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text(dateFmt.format(Date(entry.timestamp)), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textPrimary) },
                navigationIcon = {
                    IconButton(onClick = { if (isEditing) isEditing = false else onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textPrimary)
                    }
                },
                actions = {
                    if (!isEditing) {
                        if (entry.status == AnalysisStatus.ERROR) {
                            IconButton(onClick = onRetry) {
                                Icon(Icons.Default.Refresh, contentDescription = "Retry analysis", tint = textPrimary)
                            }
                        }
                        IconButton(onClick = { draftText = entry.text; isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit entry", tint = textPrimary)
                        }
                        IconButton(onClick = { onDelete(); onBack() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = textPrimary)
                        }
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
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BlobCharacter(
                    bucket = moodBucketFor(entry.moodScore),
                    seed = entry.id.toInt(),
                    modifier = Modifier.size(BlobSizes.Medium)
                )
                Spacer(Modifier.width(14.dp))
                Text(
                    entry.primaryEmotion?.replaceFirstChar { it.uppercase() } ?: "Feeling it out…",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary
                )
            }
            Spacer(Modifier.height(24.dp))

            if (stickerList.isNotEmpty() && !isEditing) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    stickerList.forEach { Text(it, fontSize = 20.sp) }
                }
                Spacer(Modifier.height(8.dp))
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Box(modifier = Modifier.background(entryBg.brush)) {
                    if (isEditing) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = draftText,
                                onValueChange = { draftText = it },
                                modifier = Modifier.fillMaxWidth().heightIn(min = 160.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary
                                ),
                                textStyle = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedButton(
                                    onClick = { isEditing = false },
                                    modifier = Modifier.weight(1f)
                                ) { Text("Cancel") }
                                Button(
                                    onClick = {
                                        if (draftText.isNotBlank() && draftText != entry.text) {
                                            onEditSave(draftText)
                                        }
                                        isEditing = false
                                    },
                                    enabled = draftText.isNotBlank(),
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkCoral else LightCoralDeep
                                    )
                                ) { Text("Save changes", color = Color.White) }
                            }
                        }
                    } else {
                        Text(
                            entry.text,
                            style = MaterialTheme.typography.bodyLarge,
                            color = textPrimary,
                            modifier = Modifier.padding(20.dp),
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            if (isEditing) return@Column

            if (entry.concernFlag) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
                ) {
                    Text(
                        CrisisSupport.SUPPORT_MESSAGE,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            when (entry.status) {
                AnalysisStatus.PENDING -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(10.dp))
                        Text("Analyzing tone…", style = MaterialTheme.typography.bodyMedium, color = textSecondary)
                    }
                }
                AnalysisStatus.ERROR -> {
                    Text(
                        "Couldn't analyze this entry. Check your API key in Settings, then tap retry above.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                AnalysisStatus.DONE -> {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        DetailSmallCard(label = "Mood score", value = "${entry.moodScore} / 5", modifier = Modifier.weight(1f))
                        val primary = entry.primaryEmotion?.replaceFirstChar { it.uppercase() } ?: "-"
                        DetailSmallCard(label = "Emotion", value = primary, modifier = Modifier.weight(1f))
                    }

                    Spacer(Modifier.height(16.dp))

                    val themeList = entry.themes?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
                    if (themeList.isNotEmpty()) {
                        Text("Themes", style = MaterialTheme.typography.labelMedium, color = textSecondary, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(themeList) { theme ->
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(theme.trim(), color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }

                    if (!entry.reflection.isNullOrBlank()) {
                        Text("Reflection", style = MaterialTheme.typography.labelMedium, color = textSecondary, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                entry.reflection!!,
                                modifier = Modifier.padding(18.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = textPrimary,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSmallCard(label: String, value: String, modifier: Modifier) {
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkText3 else LightText3

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = textSecondary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = textPrimary)
        }
    }
}
