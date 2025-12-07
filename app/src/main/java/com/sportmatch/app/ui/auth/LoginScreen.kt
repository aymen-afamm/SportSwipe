package com.sportmatch.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.*
import com.sportmatch.app.ui.viewmodel.AuthViewModel

/**
 * Premium Login Screen
 * Clean, airy design with premium spacing
 */
@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    val fadeInAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600),
        label = "fade_in"
    )

    LaunchedEffect(uiState) {
        if (uiState is com.sportmatch.app.ui.viewmodel.AuthUiState.Success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .alpha(fadeInAlpha)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.screenHorizontal)
                .padding(vertical = Spacing.xxxl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            // Logo/Icon Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Primary, SportAccent)
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Spacing.xxxl))

            // Welcome Text
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = "Sign in to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.xxxl))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = InputShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = InputShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Login Button
            PrimaryButton(
                text = "Sign In",
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is com.sportmatch.app.ui.viewmodel.AuthUiState.Loading,
                loading = uiState is com.sportmatch.app.ui.viewmodel.AuthUiState.Loading
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Sign Up Link
            TextButton(
                onClick = onNavigateToSignup,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don't have an account? Sign up",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Error Message
            if (uiState is com.sportmatch.app.ui.viewmodel.AuthUiState.Error) {
                Spacer(modifier = Modifier.height(Spacing.md))
                Text(
                    text = (uiState as com.sportmatch.app.ui.viewmodel.AuthUiState.Error).message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}