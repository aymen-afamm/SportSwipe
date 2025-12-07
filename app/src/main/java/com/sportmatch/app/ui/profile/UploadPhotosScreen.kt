package com.sportmatch.app.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.viewmodel.ProfileViewModel

@Composable
fun UploadPhotosScreen(
    onPhotosUploaded: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedPhotoIndex by remember { mutableStateOf(0) }
    val photos = remember { mutableStateListOf<Uri?>(null, null, null, null, null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photos[selectedPhotoIndex] = it
            viewModel.uploadPhoto(it, selectedPhotoIndex)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is com.sportmatch.app.ui.viewmodel.ProfileUiState.Success) {
            val user = (uiState as com.sportmatch.app.ui.viewmodel.ProfileUiState.Success).user
            if (user?.photos?.isNotEmpty() == true) {
                onPhotosUploaded()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Upload Your Photos",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            photos.take(5).forEachIndexed { index, uri ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.75f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedPhotoIndex = index; imagePicker.launch("image/*") }
                ) {
                    if (uri != null) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Photo $index",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("+")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (photos.any { it != null }) {
                    onPhotosUploaded()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = photos.any { it != null }
        ) {
            Text("Continue")
        }
    }
}
