package com.example.mywardrobe
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mywardrobe.components.WardrobeBottomNavigation
import com.example.mywardrobe.data.Brand
import com.example.mywardrobe.viewmodels.CatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardrobeScreen(viewModel: CatalogViewModel, navController: NavHostController) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("My wardrobe") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Navigation Icon"
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Actions"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Account"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
            ) {
                WardrobeBottomNavigation(1, navController)
            }
        },
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomCenter, // Positionner le menu en bas à droite (ou BottomCenter si vous préférez)
                modifier = Modifier
                    .fillMaxSize() // S'assure que la Box prend tout l'espace disponible
            ) {
                // Votre Floating Action Button ici
                FloatingActionButton(
                    onClick = { showMenu = true },
                    shape = CircleShape,
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }

                // Votre DropdownMenu ici
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier
                        .align(Alignment.TopCenter) // S'aligne en haut à droite dans la Box, juste en dessous du FAB
                ) {
                    DropdownMenuItem(onClick = {
                        //viewModel.openDialog()
                        navController.navigate("addNewItemScreen")
                        showMenu = false
                    },
                        text = { Text("Ajouter un item") }
                    )
                    DropdownMenuItem(onClick = {
                        // Logique pour ajouter un item via scan de code-barres
                        println("here on qrCodeScannerScreen option")
                        showMenu = false
                        navController.navigate("qrCodeScannerScreen")
                    },
                        text = { Text(text = "Ajouter un item via code-barre") }
                    )
                    DropdownMenuItem(onClick = {
                        showMenu = false
                        navController.navigate("createOutfitScreen")
                    },
                        text = { Text(text = "Créer une tenue") }
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        var selectedTabIndex by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                Tab(
                    text = { Text("Clothes") },
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 }
                )
                Tab(
                    text = { Text("Outfits") },
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 }
                )
                Tab(
                    text = { Text("Match maker") },
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 }
                )
            }
            // Conditionnellement afficher un sous-TabRow si l'onglet "Clothes" est sélectionné
            if (selectedTabIndex == 0) {
                SubTabRow()

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(viewModel.clotheItems) { clotheItem ->
                        ClotheItemCard(clotheItem = clotheItem, onClotheItemClick = {
                            // Action à effectuer lorsqu'un vêtement est cliqué
                            // Par exemple, ouvrir les détails du vêtement ou ajouter à une tenue
                        })
                    }
                }
            }
            else if (selectedTabIndex == 1) {
                OutfitsContent()
            }
        }

    }
}

@Composable
fun SubTabRow() {
    // Sous-onglets pour l'onglet "Clothes"
    val subTabs = listOf("All", "Tops", "Jeans", "Dresses", "Pants", "Sweat")
    var selectedSubTabIndex by remember { mutableStateOf(0) }

    ScrollableTabRow(selectedTabIndex = selectedSubTabIndex) {
        subTabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedSubTabIndex == index,
                onClick = { selectedSubTabIndex = index }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClotheItemCard(clotheItem: ClotheItem, onClotheItemClick: (ClotheItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClotheItemClick(clotheItem) }, // Gérez le clic sur la carte ici
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Affichez l'image du vêtement
            Image(
                painter = painterResource(id = clotheItem.pictureRes),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Affichez le nom du vêtement
            Text(
                text = clotheItem.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )

            // Affichez le type du vêtement
            Text(
                text = clotheItem.category.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )

            // Affichez la taille et la marque du vêtement
            Text(
                text = "Taille: ${clotheItem.size}, Marque: ${clotheItem.brand}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun OutfitsContent() {
    Column {
        // Champ de recherche
        OutfitSearchBar(onFilterSelected = {})

        // Titre de la section "Favourites"
        Text("Favourites", style = MaterialTheme.typography.headlineMedium)

        // Carte pour l'article mis en avant
        FeaturedOutfitCard()

        // Grille pour les autres articles
        OutfitGrid()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedOutfitCard() {
    // Remplacez ceci par votre implémentation de la carte pour l'article mis en avant
    Card {
        // Contenu de la carte pour l'article mis en avant
    }
}

@Composable
fun OutfitGrid() {
    // Affiche trois cartes dans une grille
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        for (i in 1..3) {
            OutfitItemCard() // Remplacez ceci par votre composant de carte pour chaque tenue
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutfitItemCard() {
    // Contenu de la carte pour chaque tenue
    Card {
        // Ajoutez ici les éléments de votre tenue, comme une image, un titre, etc.
    }
}

@Composable
fun OutfitSearchBar(onFilterSelected: (Style) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var showFilterDropdown by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.weight(1f),
            label = { Text("Search") }
        )
        IconButton(onClick = { showFilterDropdown = true }) {
            Icon(painter = painterResource(id = R.drawable.baseline_filter_list_24), contentDescription = "Filter")
        }
        DropdownMenu(
            expanded = showFilterDropdown,
            onDismissRequest = { showFilterDropdown = false }) {
            Style.values().forEach { style ->
                DropdownMenuItem(onClick = {
                    onFilterSelected(style)
                    showFilterDropdown = false
                },
                text = { Text(style.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }
    }
}


@Preview
@Composable
fun WardrobeScreenPreview() {
    val navController = rememberNavController()
    WardrobeScreen(viewModel = CatalogViewModel(), navController = navController)
}
