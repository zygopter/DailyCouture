package com.example.mywardrobe

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mywardrobe.components.ExpandableListSelector
import com.example.mywardrobe.components.GridImage
import com.example.mywardrobe.components.LinkText
import com.example.mywardrobe.data.BrandManager
import com.example.mywardrobe.viewmodels.CatalogViewModel

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
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ajouter jusqu'à 10 photos. ",
                            style = MaterialTheme.typography.caption
                        )
                        LinkText(
                            linkText = "Comment prendre mes photos?",
                            url = "https://www.colorclipping.com/fr/blog/take-pictures-of-clothes-without-mannequin"
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //ClothesImageRow()
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly)
                    {
                        Card(backgroundColor = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier
                            //.size(width = 240.dp, height = 100.dp)
                            //.fillMaxWidth()
                            .weight(1.0f)
                            .aspectRatio(1f)
                            .padding(8.dp),
                        elevation = 2.dp)
                        {
                            androidx.compose.material3.IconButton(
                                onClick = {}
                            ) {
                                Icon(painterResource(id = R.drawable.baseline_add_photo_alternate_24), contentDescription = "Prendre une photo")
                            }
                        }
                        Card(backgroundColor = MaterialTheme.colors.background,
                            modifier = Modifier
                                //.size(width = 240.dp, height = 100.dp)
                                //.fillMaxWidth()
                                .weight(1.0f)
                                .aspectRatio(1f)
                                .padding(8.dp),
                            elevation = 1.dp)
                        {
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = { /* TODO: Ajouter photo avec l'appareil photo */ }) {
                            Icon(painterResource(id = R.drawable.baseline_add_photo_alternate_24), contentDescription = "Prendre une photo")
                        }
                        IconButton(onClick = { /* TODO: Sélectionner une photo depuis la galerie */ }) {
                            Icon(painterResource(id = R.drawable.baseline_qr_code_24), contentDescription = "Sélectionner depuis la galerie")
                        }
                        IconButton(onClick = { /* TODO: Ajouter un espace réservé pour la photo */ }) {
                            Icon(painterResource(id = R.drawable.global_search_icon_243457), contentDescription = "Ajouter un espace réservé")
                        }
                        // Ajoutez d'autres boutons si nécessaire
                    }
                    // Continuez avec le reste de votre écran NewItemScreen
                }
            }
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
            ExpandableListSelector(items = BrandManager.getBrandsString(),
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

@Composable
fun ClothesImageRow(clothesImages: List<Painter>, onAddPhotoClick: () -> Unit) {
    LazyRow {
        if (clothesImages.isEmpty()) {
            item {
                Card(modifier = Modifier.padding(16.dp), elevation = 4.dp) {
                    Button(onClick = onAddPhotoClick) {
                        Text(text = "Ajouter des photos")
                    }
                }
            }
        } else {
            items(clothesImages) { image ->
                Image(
                    painter = image,
                    contentDescription = "Vêtement",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    val navController = rememberNavController()
    NewItemScreen(navController = navController, viewModel = CatalogViewModel())
}
