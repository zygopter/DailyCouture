package com.example.mywardrobe

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateOutfitScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var outfitName by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(elevation = 4.dp,
                title = { Text("Create new outfit")},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Filled.Close, null)
                    }
                })
        },
        bottomBar = {
            BottomNavigation {
                // Add BottomNavigationItems here
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                ClotheType.values().forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Log.w("CreateOutfitScreen", "clothe type value: ${title.displayName}")
                            Text(title.displayName)
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.LightGray,
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
            OutfitEditArea() // This is a placeholder for your outfit editing area
            TextField(
                value = outfitName,
                onValueChange = { outfitName = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = false,
                onDismissRequest = { /* TODO */ }
            ) {
                Style.values().forEach { style ->
                    DropdownMenuItem(onClick = { selectedStyle = style.name }) {
                        Text(style.name)
                    }
                }
            }
            Button(
                onClick = { /* TODO: Save the outfit */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("SAVE")
            }
        }
    }
}

@Composable
fun OutfitEditArea() {
    // Placeholder for your outfit editing area
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(8.dp)) {
        // Add drag-and-drop or selection logic for outfit items here
    }
}

@Preview(showBackground = true)
@Composable
fun CreateOutfitScreenPreview() {
    val navController = rememberNavController()
    CreateOutfitScreen(navController)
}
