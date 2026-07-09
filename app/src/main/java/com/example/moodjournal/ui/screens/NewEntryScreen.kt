// app/src/main/java/com/example/moodjournal/ui/screens/NewEntryScreen.kt
package com.example.moodjournal.ui.screens
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.BackgroundPickerContent
import com.example.moodjournal.ui.components.BlobCharacter
import com.example.moodjournal.ui.components.BlobSizes
import com.example.moodjournal.ui.components.MoodBucket
import com.example.moodjournal.ui.components.StickerPickerContent
import com.example.moodjournal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(
    onBack: () -> Unit,
    onSave: (String, String?, List<String>) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var selectedBackground by remember { mutableStateOf(EntryBackgrounds.first()) }
    var selectedStickers = remember { mutableStateListOf<String>() }
    var sheetMode by remember { mutableStateOf<String?>(null) } // "background" | "stickers" | null
    val sheetState = rememberModalBottomSheetState()

    val isDarkMode = MaterialTheme.colorScheme.background == DarkPageBg1
    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("New entry", fontWeight = FontWeight.Bold, color = textPrimary) },
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
                .imePadding()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BlobCharacter(
                        bucket = MoodBucket.GOOD,
                        seed = 42,
                        modifier = Modifier.size(BlobSizes.Medium)
                    )
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Hey, how's it going?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )
                        Text(
                            "Write freely — I'll gently read the tone once you save ✨",
                            style = MaterialTheme.typography.bodySmall,
                            color = textSecondary
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Customize row: background + stickers
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CustomizeChip(
                        icon = Icons.Default.Palette,
                        label = "Background",
                        isDarkMode = isDarkMode,
                        onClick = { sheetMode = "background" }
                    )
                    CustomizeChip(
                        icon = Icons.Default.EmojiEmotions,
                        label = "Stickers",
                        isDarkMode = isDarkMode,
                        onClick = { sheetMode = "stickers" }
                    )
                }

                Spacer(Modifier.height(16.dp))

                if (selectedStickers.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        selectedStickers.forEach { sticker ->
                            Text(
                                sticker,
                                fontSize = 22.sp,
                                modifier = Modifier.clickableRemove { selectedStickers.remove(sticker) }
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 400.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(selectedBackground.brush)) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            placeholder = {
                                Text("What's on your mind today? 🌙", color = textSecondary)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedTextColor = textPrimary,
                                unfocusedTextColor = textPrimary
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onSave(text, selectedBackground.id, selectedStickers.toList())
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                enabled = text.isNotBlank(),
                contentPadding = PaddingValues()
            ) {
                val pillBg = if (isDarkMode) DarkPillBg else LightPillBg
                val pillText = if (isDarkMode) Color(0xFFFFF3F6) else Color.White

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (text.isNotBlank()) pillBg else Brush.linearGradient(listOf(Color.Gray.copy(alpha = 0.3f), Color.Gray.copy(alpha = 0.3f)))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Save entry 💌", fontSize = 16.sp, color = if (text.isNotBlank()) pillText else Color.Gray, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    if (sheetMode != null) {
        ModalBottomSheet(
            onDismissRequest = { sheetMode = null },
            sheetState = sheetState,
            containerColor = if (isDarkMode) DarkSheetBg else LightSheetBg
        ) {
            when (sheetMode) {
                "background" -> BackgroundPickerContent(
                    selectedId = selectedBackground.id,
                    onSelect = { selectedBackground = it },
                    textPrimary = textPrimary
                )
                "stickers" -> StickerPickerContent(
                    selectedStickers = selectedStickers,
                    onToggle = { sticker ->
                        if (sticker in selectedStickers) selectedStickers.remove(sticker)
                        else if (selectedStickers.size < 6) selectedStickers.add(sticker)
                    },
                    textPrimary = textPrimary
                )
            }
        }
    }
}

@Composable
private fun CustomizeChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isDarkMode) DarkStatTrack else LightStatTrack
    val tint = if (isDarkMode) DarkCoral else LightCoralDeep
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .clickableChip(onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Text(label, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = tint)
    }
}

// Small local helpers to keep imports tidy without pulling in extra foundation imports above
private fun Modifier.clickableChip(onClick: () -> Unit): Modifier =
    this.then(androidx.compose.foundation.clickable(onClick = onClick))

private fun Modifier.clickableRemove(onClick: () -> Unit): Modifier =
    this.then(androidx.compose.foundation.clickable(onClick = onClick))
