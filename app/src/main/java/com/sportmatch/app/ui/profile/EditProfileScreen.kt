package com.sportmatch.app.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Date

@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val user by viewModel.user.collectAsState()

    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }
    var selectedInterests by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            bio = it.bio
            gender = it.gender
            experienceLevel = it.experienceLevel
            selectedInterests = it.interests.toSet()
        }
    }

    val sports = listOf("Running", "Football", "Basketball", "Gym", "Padel", "Cycling", "Tennis", "Swimming")
    val experienceLevels = listOf("Beginner", "Intermediate", "Pro")
    val genders = listOf("Male", "Female", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            var expandedGender by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedGender,
                onExpandedChange = { expandedGender = !expandedGender }
            ) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Gender") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) }
                )
                ExposedDropdownMenu(
                    expanded = expandedGender,
                    onDismissRequest = { expandedGender = false }
                ) {
                    genders.forEach { g ->
                        DropdownMenuItem(
                            text = { Text(g) },
                            onClick = {
                                gender = g
                                expandedGender = false
                            }
                        )
                    }
                }
            }

            var expandedLevel by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedLevel,
                onExpandedChange = { expandedLevel = !expandedLevel }
            ) {
                OutlinedTextField(
                    value = experienceLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Experience Level") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLevel) }
                )
                ExposedDropdownMenu(
                    expanded = expandedLevel,
                    onDismissRequest = { expandedLevel = false }
                ) {
                    experienceLevels.forEach { level ->
                        DropdownMenuItem(
                            text = { Text(level) },
                            onClick = {
                                experienceLevel = level
                                expandedLevel = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Sports Interests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            sports.forEach { sport ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(sport)
                    Checkbox(
                        checked = selectedInterests.contains(sport),
                        onCheckedChange = { checked ->
                            selectedInterests = if (checked) {
                                selectedInterests + sport
                            } else {
                                selectedInterests - sport
                            }
                        }
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.updateProfile(
                        name = name,
                        bio = bio,
                        gender = gender,
                        interests = selectedInterests.toList(),
                        experienceLevel = experienceLevel,
                        dateOfBirth = user?.dateOfBirth,
                        preferences = user?.preferences
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState !is ProfileUiState.Loading
            ) {
                if (uiState is ProfileUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Save")
                }
            }
        }
    }
}

