package com.sportmatch.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sportmatch.app.data.model.UserModel

@Composable
fun ProfileHeader(
    user: UserModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user.photos.isNotEmpty()) {
            AsyncImage(
                model = user.photos.first(),
                contentDescription = user.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${user.name}, ${user.age}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        if (user.experienceLevel.isNotEmpty()) {
            Text(
                text = user.experienceLevel,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

