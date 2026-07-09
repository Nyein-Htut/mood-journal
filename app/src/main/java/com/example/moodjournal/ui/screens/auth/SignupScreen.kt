package com.example.moodjournal.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    onSignupSuccess: (String, String) -> Unit,
    onBack: () -> Unit,
    isDarkMode: Boolean,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
    val localErrorState = remember { mutableStateOf<String?>(null) }

    val shownError = errorMessage ?: localErrorState.value

    val backgroundBrush = if (isDarkMode) DarkPageGradient else LightPageGradient
    val textPrimary = if (isDarkMode) DarkText1 else LightText1
    val textSecondary = if (isDarkMode) DarkText3 else LightText3

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Create Account", fontWeight = FontWeight.Bold, color = textPrimary) },
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
        .verticalScroll(rememberScrollState())
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
)  {
            Text("✨", fontSize = 48.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "Start your journey",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )
            Text(
                "Pick a username to sync your logs to the cloud",
                color = textSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it; localErrorState.value = null },
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
                value = passwordState.value,
                onValueChange = { passwordState.value = it; localErrorState.value = null },
                label = { Text("Password (min 6 chars)") },
                visualTransformation = PasswordVisualTransformation(),
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
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it; localErrorState.value = null },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedBorderColor = if (isDarkMode) DarkCoral else LightCoralDeep
                )
            )

            if (shownError != null) {
                Text(
                    shownError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val username = usernameState.value
                    val password = passwordState.value
                    val confirmPassword = confirmPasswordState.value
                    when {
                        username.isBlank() -> localErrorState.value = "Please enter a username"
                        password != confirmPassword -> localErrorState.value = "Passwords do not match"
                        password.length < 6 -> localErrorState.value = "Password must be at least 6 characters"
                        else -> {
                            localErrorState.value = null
                            onSignupSuccess(username.trim(), password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isDarkMode) DarkCoral else LightCoralDeep),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text("Create Account", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
