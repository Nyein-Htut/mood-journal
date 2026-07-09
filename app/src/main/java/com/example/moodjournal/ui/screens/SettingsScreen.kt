package com.example.moodjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentKey: String?,
    onBack: () -> Unit,
    onSave: (String) -> Unit,
    onLogout: () -> Unit
) {
    var key by remember { mutableStateOf(currentKey ?: "") }
    var showKey by remember { mutableStateOf(false) }
    val backgroundBrush = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkPageGradient else LightPageGradient
    val textPrimary = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkText3 else LightText3

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold, color = textPrimary) },
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
            Text("Groq API key", style = MaterialTheme.typography.titleMedium, color = textPrimary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Create a free key at console.groq.com. It's stored encrypted, only on " +
                "this device, and is sent directly to Groq's API when an entry is analyzed.",
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = key,
                onValueChange = { key = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("API key") },
                visualTransformation = if (showKey) androidx.compose.ui.text.input.VisualTransformation.None
                                       else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showKey = !showKey }) {
                        Text(if (showKey) "Hide" else "Show", color = MaterialTheme.colorScheme.primary)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
            Spacer(Modifier.height(24.dp))
            
            Button(
                onClick = { onSave(key.trim()); onBack() },
                enabled = key.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                val pillBg = if (MaterialTheme.colorScheme.background == DarkPageBg1) DarkPillBg else LightPillBg
                val pillText = if (MaterialTheme.colorScheme.background == DarkPageBg1) Color(0xFFFFF3F6) else Color.White
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (key.isNotBlank()) pillBg else Brush.linearGradient(listOf(Color.Gray.copy(alpha = 0.3f), Color.Gray.copy(alpha = 0.3f)))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Save", color = if (key.isNotBlank()) pillText else Color.Gray, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(40.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            Spacer(Modifier.height(24.dp))
            Text(
                "Privacy note",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Your journal entries are stored only on this device. When you save an " +
                "entry, its text is sent to Groq's API for tone analysis. Nothing is " +
                "shared with anyone else.",
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                lineHeight = 20.sp
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }
        }
    }
}
