package com.sportmatch.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.components.SportTextField
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.Tertiary
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.AuthViewModel

/**
 * LoginScreen - Email/password authentication screen.
 * Features email and password inputs with validation feedback.
 *
 * @param onNavigateToSignup Callback to navigate to signup screen
 * @param onLoginSuccess Callback when login completes successfully
 * @param viewModel AuthViewModel for handling authentication logic
 */
@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState is AuthUiState.Loading

    val fadeInAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600),
        label = "fade_in"
    )

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.screen_padding_horizontal)
                .padding(vertical = Dimens.screen_padding_vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            // Logo/Icon Placeholder
            Box(
                modifier = Modifier
                    .size(Dimens.avatar_size_extra_large)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Primary, Tertiary)
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Dimens.extra_large))

            // Welcome Text
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimens.small))

            Text(
                text = "Sign in to continue finding sports partners",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimens.extra_large))

            // Email Input
            SportTextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = false
                },
                placeholder = "Email address",
                modifier = Modifier.fillMaxWidth()
            )
            
            if (emailError) {
                Text(
                    text = "Please enter a valid email",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = Dimens.small)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.large))

            // Password Input
            SportTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = false
                },
                placeholder = "Password",
                modifier = Modifier.fillMaxWidth()
            )
            
            if (passwordError) {
                Text(
                    text = "Password must be at least 6 characters",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = Dimens.small)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.extra_large))

            // Login Button
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.icon_size_large),
                    color = Primary
                )
            } else {
                PrimaryButton(
                    text = "Login",
                    onClick = {
                        // Validate inputs
                        emailError = email.isEmpty() || !email.contains("@")
                        passwordError = password.length < 6
                        
                        if (!emailError && !passwordError) {
                            viewModel.loginWithEmail(email, password)
                        }
                    },
                    enabled = email.isNotEmpty() && password.isNotEmpty()
                )
            }

            Spacer(modifier = Modifier.height(Dimens.large))

            // Forgot Password Link
            TextButton(onClick = { /* TODO: Implement forgot password */ }) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Signup Link
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(onClick = onNavigateToSignup) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(Dimens.large))
        }
    }
}

// Placeholder for AuthUiState - define in ViewModel
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SportMatchTheme {
        LoginScreen(
            onNavigateToSignup = { },
            onLoginSuccess = { }
        )
    }
}

