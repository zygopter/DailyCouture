import androidx.compose.foundation.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mywardrobe.viewmodels.CatalogViewModel
import com.example.mywardrobe.ClotheItem
import com.example.mywardrobe.ClotheType
import com.example.mywardrobe.R
import com.example.mywardrobe.data.Brand

@Composable
fun CatalogScreen(clotheItems: List<ClotheItem>, onClotheItemClick: (ClotheItem) -> Unit, viewModel: CatalogViewModel) {
    // État pour contrôler la visibilité du menu déroulant
    var showMenu by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(clotheItems) { clotheItem ->
            // Afficher chaque vêtement dans la liste
            ClotheItemCard(clotheItem = clotheItem, onClotheItemClick = onClotheItemClick)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Floating action button
        LargeFloatingActionButton(
            onClick = {
                showMenu = true
            },
            modifier = Modifier
                .size(72.dp),
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }

        // Menu déroulant
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.width(200.dp) // Modifier la largeur si nécessaire
        ) {
            DropdownMenuItem(onClick = {
                // Logique pour ajouter un item via scan de code-barres
                showMenu = false
            },
                text = { Text(text = "Scanner un code-barre") }
            )
            DropdownMenuItem(onClick = {
                viewModel.openDialog()
                showMenu = false
            },
            text = { Text("Ajouter manuellement")})
        }
    }
    // Boîte de dialogue pour créer un nouvel objet ClotheItem
    if (viewModel.isDialogOpen) {
        CreateClotheItemDialog(
            clotheItem = viewModel.newClotheItem,
            onClotheItemChanged = { viewModel.updateNewClotheItem(it) },
            onDialogDismiss = { viewModel.isDialogOpen = false },
            onSaveClick = {
                println("onSaveClick lambda")
                viewModel.saveClotheItem(it)}
        )
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
                text = clotheItem.type.name,
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateClotheItemDialog(
    clotheItem: ClotheItem,
    onClotheItemChanged: (ClotheItem) -> Unit,
    onDialogDismiss: () -> Unit,
    onSaveClick: (ClotheItem) -> Unit
) {
    Dialog(
        onDismissRequest = { onDialogDismiss() }
    ) {
        var itemName by remember { mutableStateOf(clotheItem.title) }
        var itemType by remember { mutableStateOf(clotheItem.type) }
        var itemSize by remember { mutableStateOf(clotheItem.size) }
        var itemBrand by remember { mutableStateOf(clotheItem.brand) }
        // Liste des types de vêtements pour le menu déroulant
        val clotheTypes = ClotheType.values().toList()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            // Contenu de la boîte de dialogue pour créer un nouvel objet ClotheItem
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = {
                        itemName = it
                        onClotheItemChanged(clotheItem.copy(title = it))
                    },
                    label = { Text("Nom du vêtement") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                Row(
                    Modifier
                        .background(
                            color = Color.Transparent,
                            RoundedCornerShape(
                                8.dp
                            )
                        )
                        .border(1.dp, Color.Blue, RoundedCornerShape(10.dp))
                        .padding(
                            end = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var selectedIndex by remember { mutableStateOf(0) }
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                clotheTypes[selectedIndex].name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        },
                        onClick = { expanded = true },
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = "",
                                modifier = Modifier.background(
                                    colorResource(id = R.color.white),
                                    shape = CircleShape
                                )
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            println("Dismiss requested")
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp)
                    ) {
                        clotheTypes.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                onClick = {
                                    println("Item clicked: $s")
                                    selectedIndex = index
                                    expanded = false
                                    itemType = s
                                },
                                text = {
                                    Text(
                                        clotheTypes[index].name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = itemSize,
                    onValueChange = {
                        itemSize = it
                        onClotheItemChanged(clotheItem.copy(size = it))
                    },
                    label = { Text("Taille du vêtement") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = itemBrand.name,
                    onValueChange = {
                        itemBrand = Brand(it)
                        onClotheItemChanged(clotheItem.copy(brand = Brand(it)))
                    },
                    label = { Text("Marque du vêtement") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Bouton "Annuler"
                    Button(
                        onClick = {
                            onDialogDismiss()
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Annuler")
                    }
                    // Bouton pour enregistrer le nouvel objet ClotheItem
                    Button(
                        onClick = {
                            val updatedClotheItem = ClotheItem(
                                pictureRes = R.drawable.placeholder_image,
                                title = itemName,
                                type = itemType,
                                size = itemSize,
                                brand = itemBrand,
                                storedPlace = ""
                            )
                            onSaveClick(updatedClotheItem)
                            onDialogDismiss()
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Enregistrer")
                    }
                }
            }
        }
    }
}

