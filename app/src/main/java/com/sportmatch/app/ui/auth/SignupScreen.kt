package com.sportmatch.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.sportmatch.app.utils.toFormattedString
import java.util.Calendar
import java.util.Date

/**
 * Premium Signup Screen
 * Clean, airy design with premium spacing
 */
@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    onSignupSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf<Long?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val fadeInAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600),
        label = "fade_in"
    )

    LaunchedEffect(uiState) {
        if (uiState is com.sportmatch.app.ui.viewmodel.AuthUiState.Success) {
            onSignupSuccess()
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
                .verticalScroll(scrollState)
                .padding(horizontal = Spacing.screenHorizontal)
                .padding(vertical = Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Spacing.lg))

            // Logo/Icon Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Primary, Tertiary)
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Welcome Text
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = "Join SportMatch and find your perfect sports partner",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.xxxl))

            // Name Input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = CornerMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = CornerMedium,
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
                shape = CornerMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Date of Birth Picker
            DatePickerDialog(
                onDateSelected = { timestamp ->
                    dateOfBirth = timestamp
                },
                label = "Date of Birth",
                selectedDate = dateOfBirth
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Sign Up Button
            PrimaryButton(
                text = "Create Account",
                onClick = {
                    dateOfBirth?.let {
                        val date = Date(it)
                        viewModel.signUp(email, password, name, date)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is com.sportmatch.app.ui.viewmodel.AuthUiState.Loading && dateOfBirth != null,
                loading = uiState is com.sportmatch.app.ui.viewmodel.AuthUiState.Loading
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Login Link
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? Sign in",
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

            Spacer(modifier = Modifier.height(Spacing.xl))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (Long) -> Unit,
    label: String,
    selectedDate: Long?
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateState by remember { mutableStateOf<Long?>(selectedDate) }

    OutlinedTextField(
        value = selectedDateState?.let { Date(it).toFormattedString() } ?: "",
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true },
        readOnly = true,
        shape = CornerMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )

    if (showDialog) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateState,
            yearRange = (year - 100)..year
        )

        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDateState = it
                            onDateSelected(it)
                        }
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}