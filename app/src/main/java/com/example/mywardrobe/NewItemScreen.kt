package com.example.mywardrobe

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.compose.rememberImagePainter
import com.example.mywardrobe.components.*
import com.example.mywardrobe.data.BrandManager
import com.example.mywardrobe.data.StoragePlaceManager
import com.example.mywardrobe.data.sizeHierarchy
import com.example.mywardrobe.viewmodels.CatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemScreen(navController: NavController, viewModel: CatalogViewModel) {
    var itemName by remember { mutableStateOf("") }
    var itemCategory by remember { mutableStateOf("") }
    var itemSize by remember { mutableStateOf("") }
    var itemBrand by remember { mutableStateOf("") }
    var itemLocation by remember { mutableStateOf("") }
    var imageList by remember { mutableStateOf(listOf<Painter>()) }

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

                    ClothesImageRow()

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
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Champs de formulaire
            TextField(
                value = itemName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { itemName = it },
                label = { Text("Name") }
            )
            TextField(
                value = itemCategory,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { itemCategory = it },
                label = { Text("Category") }
            )
            DropdownMenuListSelector(
                items = sizeHierarchy,
                onItemSelected = { itemSize = it},
                onAddItem = {},
                "Choisir une taille"
            )
            ExpandableListWithIconSelector(items = StoragePlaceManager.getStoragePlacesAsMap(),
                onItemSelected = { itemBrand = it },
                onAddItem = { newStoragePlace ->
                    StoragePlaceManager.addStoragePlaces(context, newStoragePlace)
                },
                label = "Storage place" )
            ExpandableListSelector(items = BrandManager.getBrandsString(),
                onItemSelected = { itemBrand = it },
                onAddItem = { newBrand ->
                    BrandManager.addBrand(context, newBrand)
                },
                label = "Brand" )


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
fun ClothesImageRow() {
    var imageUris by remember {
        mutableStateOf(listOf<Uri>())
    }
    //The URI of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        photoUri = uri
        if (uri != null) {
            imageUris += uri
        }
    }

    LazyRow(modifier = Modifier.padding(8.dp).height(200.dp)) {
        items(imageUris) { imageUri ->
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUri)
                    .build()
            )
            Card(
                backgroundColor = BeigeColors.surface,
                modifier = Modifier
                    .padding(4.dp)
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
        item {
            Card(
                backgroundColor = BeigeColors.onPrimary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                elevation = 2.dp)
            {
                androidx.compose.material3.IconButton(
                    onClick = {
                        //On button press, launch the photo picker
                        launcher.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Icon(painterResource(id = R.drawable.baseline_add_photo_alternate_24), contentDescription = "Prendre une photo")
                }
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
