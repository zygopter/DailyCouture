package com.example.mywardrobe

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mywardrobe.components.*
import com.example.mywardrobe.data.*
import com.example.mywardrobe.viewmodels.CatalogViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemScreen(navController: NavController, viewModel: CatalogViewModel, itemId: String? = null) {
    val storedItemLiveData = viewModel.getItemById(itemId ?: "")
    val storedItem by storedItemLiveData.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    var itemName by remember { mutableStateOf("") }
    var itemClothingCategory by remember { mutableStateOf(ClothingCategory("")) }
    var itemSize by remember { mutableStateOf(Size("", "")) }
    var itemBrand by remember { mutableStateOf("") }
    var itemLocation by remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf(listOf<Uri>()) }
    var imagePaths by remember {
        mutableStateOf(listOf<String>())
    }

    LaunchedEffect(itemId) {
        // Assurez-vous que l'itemId est non null et lancez une action pour le charger
        itemId?.let {
            Log.i("NewItemScreen", "Entering new item screen with preselected item: $itemId")
            viewModel.getItemById(it)
        }
    }
    LaunchedEffect(storedItem) {
        isLoading = true
        itemName = storedItem?.title ?: ""
        itemClothingCategory = storedItem?.category ?: ClothingCategory("")
        itemSize = storedItem?.size ?: Size("", "")
        itemBrand = storedItem?.brand ?: ""
        itemLocation = storedItem?.storedPlace ?: ""
        imageUris = storedItem?.pictures ?: listOf()
        imagePaths = storedItem?.picturePaths ?: listOf()

        isLoading = false
    }

    val focusManager = LocalFocusManager.current

    // Obtenez le contexte local
    val context = LocalContext.current

    if (isLoading) {
        CircularProgressIndicator() // Affiche un indicateur de chargement
    } else {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 4.dp,
                    title = { Text("Add new item") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.Close, null)
                        }
                    })
            },
            bottomBar = { /* ... Bottom Navigation ... */ }
        ) {
            Column(modifier = Modifier
                .padding(8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    focusManager.clearFocus()
                }) {
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

                        ClothesImageRow(imagePaths) { uris ->
                            imageUris = uris
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(onClick = { /* TODO: Ajouter photo avec l'appareil photo */ }) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_add_photo_alternate_24),
                                    contentDescription = "Prendre une photo"
                                )
                            }
                            IconButton(onClick = { /* TODO: Sélectionner une photo depuis la galerie */ }) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_qr_code_24),
                                    contentDescription = "Sélectionner depuis la galerie"
                                )
                            }
                            IconButton(onClick = { /* TODO: Ajouter un espace réservé pour la photo */ }) {
                                Icon(
                                    painterResource(id = R.drawable.global_search_icon_243457),
                                    contentDescription = "Ajouter un espace réservé"
                                )
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            focusManager.clearFocus()
                        },
                    state = rememberLazyListState()
                ) {
                    item {
                        TextField(
                            value = itemName,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { itemName = it },
                            label = { Text("Name") }
                        )
                    }
                    item {
                        HierarchicalDropdownMenu(categories = clothingCategories,
                            itemClothingCategory,
                            onCategorySelected = { itemClothingCategory = it }
                        )
                        SizeCategorySelector(
                            items = sizeHierarchy,
                            itemSize,
                            onItemSelected = { itemSize = it },
                            "Choisir une taille"
                        )
                        ExpandableListWithIconSelector(
                            items = StoragePlaceManager.getStoragePlacesAsMap(),
                            preselectedItem = itemLocation,
                            onItemSelected = { itemLocation = it },
                            onAddItem = { newStoragePlace ->
                                StoragePlaceManager.addStoragePlaces(context, newStoragePlace)
                            },
                            label = "Storage place"
                        )
                        ExpandableListSelector(
                            items = BrandManager.getBrandsString(),
                            preselectedItem = itemBrand,
                            onItemSelected = { itemBrand = it },
                            onAddItem = { newBrand ->
                                BrandManager.addBrand(context, newBrand)
                            },
                            label = "Brand"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bouton pour sauvegarder
                Button(
                    onClick = {
                        imagePaths = imageUris.map {
                            storeImage(it, itemName, context)
                        }
                        if (storedItem == null) {
                            val newClotheItem = ClotheItem(
                                pictures = imageUris,
                                picturePaths = imagePaths,
                                title = itemName,
                                category = itemClothingCategory,
                                size = itemSize,
                                brand = itemBrand,
                                storedPlace = itemLocation
                            )
                            viewModel.saveClotheItem(newClotheItem)
                        } else {
                            // Mise à jour de l'item existant
                            viewModel.updateStoredItem(
                                storedItem!!.copy(
                                    pictures = imageUris,
                                    picturePaths = imagePaths,
                                    title = itemName,
                                    category = itemClothingCategory,
                                    size = itemSize,
                                    brand = itemBrand,
                                    storedPlace = itemLocation
                                )
                            )
                        }
                        navController.navigate("wardrobeScreen")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SAVE")
                }
            }
        }
    }
}


@Composable
fun ClothesImageRow(previousPictures: List<String>, onUrisUpdated: (photoUris: List<Uri>) -> Unit) {
    var imageUris by remember {
        mutableStateOf(listOf<Uri>())
    }
    val previousUris = previousPictures.map { Uri.fromFile(File(it)) }
    imageUris = previousUris
    //The URI of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        photoUri = uri
        if (uri != null) {
            imageUris = imageUris + uri
            onUrisUpdated(imageUris)
        }
    }

    LazyRow(modifier = Modifier
        .padding(8.dp)
        .height(200.dp)) {
        items(imageUris) { imageUri ->
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUri)
                    .build()
            )
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
            ) {
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
                IconButton(
                    onClick = {
                        imageUris = imageUris - imageUri
                        onUrisUpdated(imageUris)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(28.dp)
                        .background(
                            color = MaterialTheme.colors.secondaryVariant,
                            shape = CircleShape
                        )
                        .padding(5.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Supprimer la photo")
                }
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

// Fonction pour compresser et stocker une image
fun storeImage(imageUri: Uri, name: String, context: Context): String {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Compresser l'image
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeStream(inputStream, null, options)
    val originalHeight = options.outHeight
    val originalWidth = options.outWidth
    val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

    val displayMetrics = Resources.getSystem().displayMetrics
    val newWidth = displayMetrics.widthPixels
    val newHeight = (newWidth / aspectRatio).toInt()

    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)

    // Créer un fichier pour stocker l'image
    val nameEncoded = URLEncoder.encode(createFileNameWithTimestamp(name, "jpg"), "UTF-8").replace("+", "_")
    val imageFile = File(context.filesDir, nameEncoded)
    val outputStream = FileOutputStream(imageFile)
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
    outputStream.close()

    return imageFile.absolutePath
}

fun createFileNameWithTimestamp(baseName: String, extension: String): String {
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault())
    val timestamp = dateFormat.format(Date())
    return "${baseName}_${timestamp}.${extension}"
}

/*@Preview
@Composable
fun ComposablePreview() {
    val navController = rememberNavController()
    val fakeViewModel = CatalogViewModel(repository = null)
    NewItemScreen(navController = navController, viewModel = fakeViewModel)
}*/
