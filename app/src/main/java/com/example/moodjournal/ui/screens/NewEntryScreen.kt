package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.BlobCharacter
import com.example.moodjournal.ui.components.BlobSizes
import com.example.moodjournal.ui.components.MoodBucket
import com.example.moodjournal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(
    onBack: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    
    val backgroundBrush = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkText3 else LightText3

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
        // Use a Column with weight and imePadding to ensure the button stays visible
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
                .imePadding() // Adjust for keyboard
                .padding(horizontal = 20.dp)
        ) {
            // Inner content wrapped in a scrollable column to handle small screens when keyboard is up
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

                Spacer(Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 400.dp), // Set a reasonable height range
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
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
                Spacer(Modifier.height(20.dp))
            }

            // Keep the button at the bottom of the screen, but above the keyboard
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onSave(text)
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
                val pillBg = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkPillBg else LightPillBg
                val pillText = if (MaterialTheme.colorScheme.background == DarkPageBg1) Color(0xFFFFF3F6) else Color.White
                
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
}
