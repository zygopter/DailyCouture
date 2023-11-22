package com.example.mywardrobe.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mywardrobe.R

@Composable
fun ClothePicturesRow(pictureUris: List<Uri>) {
    LazyRow(modifier = Modifier
        .padding(4.dp)
        .height(140.dp)) {
        items(pictureUris) { imageUri ->
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUri)
                    .build()
            )
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .aspectRatio(1f),
                elevation = 2.dp
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Inside
                )
            }
        }
        if (pictureUris.isEmpty()) {
            item {
                Card(
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .aspectRatio(1f),
                    elevation = 2.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_insert_photo_24_grey),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp),
                        contentScale = ContentScale.Inside
                    )
                }
            }
        }
    }
}
