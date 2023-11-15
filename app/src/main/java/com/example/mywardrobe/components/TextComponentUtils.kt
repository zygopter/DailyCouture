package com.example.mywardrobe.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun LinkText(linkText: String, url: String) {
    val context = LocalContext.current
    Text(
        text = linkText,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelSmall,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        }
    )
}