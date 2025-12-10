//package com.sportmatch.app.ui.profile
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.sportmatch.app.ui.viewmodel.ProfileViewModel
//
//@Composable
//fun UploadPhotosScreen(
//    onPhotosUploaded: () -> Unit,
//    viewModel: ProfileViewModel = hiltViewModel()
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    var selectedPhotoIndex by remember { mutableStateOf(0) }
//    val photos = remember { mutableStateListOf<Uri?>(null, null, null, null, null) }
//
//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            photos[selectedPhotoIndex] = it
//            viewModel.uploadPhoto(it, selectedPhotoIndex)
//        }
//    }
//
//    LaunchedEffect(uiState) {
//        if (uiState is com.sportmatch.app.ui.viewmodel.ProfileUiState.Success) {
//            val user = (uiState as com.sportmatch.app.ui.viewmodel.ProfileUiState.Success).user
//            if (user?.photos?.isNotEmpty() == true) {
//                onPhotosUploaded()
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Upload Your Photos",
//            style = MaterialTheme.typography.headlineLarge,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            photos.take(5).forEachIndexed { index, uri ->
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(0.75f)
//                        .clip(RoundedCornerShape(8.dp))
//                        .clickable { selectedPhotoIndex = index; imagePicker.launch("image/*") }
//                ) {
//                    if (uri != null) {
//                        AsyncImage(
//                            model = uri,
//                            contentDescription = "Photo $index",
//                            modifier = Modifier.fillMaxSize(),
//                            contentScale = ContentScale.Crop
//                        )
//                    } else {
//                        Surface(
//                            modifier = Modifier.fillMaxSize(),
//                            color = MaterialTheme.colorScheme.surfaceVariant
//                        ) {
//                            Column(
//                                modifier = Modifier.fillMaxSize(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Text("+")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Button(
//            onClick = {
//                if (photos.any { it != null }) {
//                    onPhotosUploaded()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            enabled = photos.any { it != null }
//        ) {
//            Text("Continue")
//        }
//    }
//}






package com.sportmatch.app.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.CornerCard
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.DividerLight
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.viewmodel.ProfileViewModel

@Composable
fun UploadPhotosScreen(
    onPhotosUploaded: (() -> Unit)? = null,
    onNavigateToSwipe: (() -> Unit)? = null,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val photos = remember { mutableStateListOf<Uri?>(null, null, null, null, null) }
    var selectedPhotoIndex by remember { mutableStateOf(0) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photos[selectedPhotoIndex] = it
            viewModel.uploadPhoto(it, selectedPhotoIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(Dimens.screen_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimens.large))

        Text(
            text = "Upload Your Photos",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(Dimens.small))

        Text(
            text = "Add 1-5 photos to help others get to know you better",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimens.large),
            verticalArrangement = Arrangement.spacedBy(Dimens.large),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) { index ->
                PhotoSlot(
                    imageUrl = photos[index],
                    onAddPhoto = { selectedPhotoIndex = index; imagePicker.launch("image/*") },
                    onRemovePhoto = { photos[index] = null }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        PrimaryButton(
            text = "Continue",
            onClick = {
                onPhotosUploaded?.invoke()
                onNavigateToSwipe?.invoke()
            },
            enabled = photos.any { it != null }
        )

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Composable
private fun PhotoSlot(
    imageUrl: Uri?,
    onAddPhoto: () -> Unit,
    onRemovePhoto: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(CornerCard)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onAddPhoto() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add photo",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
