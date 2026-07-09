package com.example.moodjournal.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onContinueAsGuest: () -> Unit,
    onSignUpClick: () -> Unit,
    isDarkMode: Boolean,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Scaffold(containerColor = Color.Transparent) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🌸", fontSize = 64.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "Mood Journal",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )
            Text(
                "Your emotional story begins here",
                color = textSecondary,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(48.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedBorderColor = if (isDarkMode) DarkCoral else LightCoralDeep
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedBorderColor = if (isDarkMode) DarkCoral else LightCoralDeep
                )
            )

            if (errorMessage != null) {
                Text(
                    errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onLoginClick(username.trim(), password) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isDarkMode) DarkCoral else LightCoralDeep),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Login", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            TextButton(onClick = onSignUpClick) {
                Text("New here? Create an account", color = textSecondary)
            }

            Spacer(Modifier.height(32.dp))
            
            HorizontalDivider(color = textSecondary.copy(alpha = 0.2f))
            
            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = onContinueAsGuest,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, textSecondary.copy(alpha = 0.5f))
            ) {
                Text("Continue as Guest", color = textPrimary, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
