// app/src/main/java/com/example/moodjournal/ui/components/EntryCustomizeSheets.kt
package com.example.moodjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.theme.AvailableStickers
import com.example.moodjournal.ui.theme.EntryBackground
import com.example.moodjournal.ui.theme.EntryBackgrounds

@Composable
fun BackgroundPickerContent(
    selectedId: String,
    onSelect: (EntryBackground) -> Unit,
    textPrimary: Color
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text("Choose a background", fontWeight = FontWeight.Bold, color = textPrimary, modifier = Modifier.padding(bottom = 12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.heightIn(max = 260.dp)
        ) {
            items(EntryBackgrounds) { bg ->
                val selected = bg.id == selectedId
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(bg.brush)
                        .border(
                            width = if (selected) 3.dp else 1.dp,
                            color = if (selected) Color(0xFFE14D74) else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { onSelect(bg) },
                    contentAlignment = Alignment.Center
                ) {
                    if (selected) Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFFE14D74))
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun StickerPickerContent(
    selectedStickers: List<String>,
    onToggle: (String) -> Unit,
    textPrimary: Color
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text("Add stickers", fontWeight = FontWeight.Bold, color = textPrimary, modifier = Modifier.padding(bottom = 12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.heightIn(max = 220.dp)
        ) {
            items(AvailableStickers) { sticker ->
                val selected = sticker in selectedStickers
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(if (selected) Color(0xFFE14D74).copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.08f))
                        .clickable { onToggle(sticker) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(sticker, fontSize = 22.sp)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}
