package com.example.mywardrobe.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.mywardrobe.data.ClothingCategory
import com.example.mywardrobe.data.ClothingCategoryNode
import com.example.mywardrobe.data.Size
import com.example.mywardrobe.data.SizeCategory

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun SizeCategorySelector(items: List<SizeCategory>,
                         onItemSelected: (Size) -> Unit,
                         label: String) {
    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf("")
    }

    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = Modifier.fillMaxWidth(),
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedItem,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { category ->
                DropdownMenuItem(
                    onClick = { /* non interactif */ },
                    enabled = false // rend l'élément non interactif
                ) {
                    Text(category.name, style = MaterialTheme.typography.subtitle1)
                }
                category.sizes.forEach { size ->
                    DropDownMenuItemWithSelectedItem(
                        name = "$size (${category.name})",
                        displayName = size,
                        selectedItemName = selectedItem
                    ) {
                        selectedItem = "$size (${category.name})"
                        onItemSelected(Size(category = category.name, size = size))
                        expanded = false
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun ClothingCategorySelector(items: List<ClothingCategoryNode>,
                             onItemSelected: (String) -> Unit,
                             label: String) {
    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf("")
    }

    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = Modifier.fillMaxWidth(),
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedItem,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = category.name
                        onItemSelected(selectedItem)
                        expanded = false
                    }
                ) {
                    Text(category.name)
                }
            }
        }
    }
}

fun flattenCategories(categories: List<ClothingCategoryNode>, indent: String = ""): List<Pair<String, String>> {
    val flattened = mutableListOf<Pair<String, String>>()
    for (category in categories) {
        val displayName = indent + category.name
        flattened.add(Pair(displayName, category.name))
        flattened.addAll(flattenCategories(category.subCategories, indent + "\t\t "))
    }
    return flattened
}
fun flattenClothingCategories(categories: List<ClothingCategoryNode>, path: String = "/", indent: String = ""): List<ClothingCategory> {
    val flattened = mutableListOf<ClothingCategory>()
    for (category in categories) {
        val displayName = indent + category.name
        val fullName = path + category.name
        flattened.add(ClothingCategory(fullName, displayName))
        flattened.addAll(flattenClothingCategories(category.subCategories, "$fullName/",
            "$indent\t\t "
        ))
    }
    return flattened
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HierarchicalDropdownMenu(categories: List<ClothingCategoryNode>, onCategorySelected: (ClothingCategory) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    // Créer une structure linéaire avec indentation pour représenter la hiérarchie
    val flattenedCategories = flattenClothingCategories(categories)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = selectedCategory.split("/").last(),
            onValueChange = { },
            label = { Text("Choisir une catégorie") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = expanded
            ) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            flattenedCategories.forEach { category ->
                DropDownMenuItemWithSelectedItem(
                    name = category.fullName,
                    displayName = category.displayName,
                    selectedItemName = selectedCategory,
                    onItemSelected = {
                        selectedCategory = category.fullName
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DropDownMenuItemWithSelectedItem(name: String, displayName: String, selectedItemName: String, onItemSelected: () -> Unit) {
    DropdownMenuItem(
        onClick = {
            onItemSelected()
        }
    ) {
        Text(
            text = displayName,
            fontWeight = if (name == selectedItemName) FontWeight.Bold else FontWeight.Normal,
            color = if (name == selectedItemName) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
        )
    }
}