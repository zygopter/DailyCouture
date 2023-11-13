package com.example.mywardrobe

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywardrobe.components.ExpandableListSelector
import com.example.mywardrobe.components.MyExposedDropdownMenu
import com.example.mywardrobe.data.BrandManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemScreen(navController: NavController, viewModel: CatalogViewModel) {
    var itemName by remember { mutableStateOf("") }
    var itemCategory by remember { mutableStateOf("") }
    var itemSize by remember { mutableStateOf("") }
    var itemBrand by remember { mutableStateOf("") }
    var itemLocation by remember { mutableStateOf("") }

    // Obtenez le contexte local
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(elevation = 4.dp,
                title = { Text("Add new item")},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Filled.Close, null)
                    }
                })
        },
        bottomBar = { /* ... Bottom Navigation ... */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Boutons pour ajouter des photos, scanner, etc.
            OutlinedButton(onClick = { /* TODO */ }) {
                Text("Ajouter photos")
            }
            // ... autres boutons

            Spacer(modifier = Modifier.height(16.dp))

            // Champs de formulaire
            TextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Name") }
            )
            MyExposedDropdownMenu(items = BrandManager.getBrandsString(),
                onItemSelected = { itemBrand = it },
                onAddItem = { newBrand ->
                    BrandManager.addBrand(context, newBrand)
                    // Ici, sauvegardez la liste mise à jour
                },
                label = "Brand" )
            // ... autres champs de formulaire

            Spacer(modifier = Modifier.height(16.dp))

            // Bouton pour sauvegarder
            Button(
                onClick = { /* TODO: Save the item */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SAVE")
            }
        }
    }
}


