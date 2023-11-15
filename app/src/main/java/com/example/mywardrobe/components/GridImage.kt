package com.example.mywardrobe.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun GridImage(icons: List<Icon>, onClicks: List<() -> Unit>) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        IconButton(onClick = onClicks[0],
            modifier = Modifier
                .weight(1.0f)
                .aspectRatio(1f)) {
            icons[0]
        }
        IconButton(onClick = onClicks[1],
            modifier = Modifier
                .weight(1.0f)
                .aspectRatio(1f)) {
            icons[1]
        }
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        IconButton(onClick = onClicks[2],
            modifier = Modifier
                .weight(1.0f)
                .aspectRatio(1f)) {
            icons[2]
        }
        IconButton(onClick = onClicks[3],
            modifier = Modifier
                .weight(1.0f)
                .aspectRatio(1f)) {
            icons[3]
        }
    }
}