package com.example.mywardrobe.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mywardrobe.R
import com.example.mywardrobe.data.SizeCategory

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun DropdownMenuListSelector(items: List<SizeCategory>,
                            onItemSelected: (String) -> Unit,
                            onAddItem: (String) -> Unit,
                            label: String) {
    val contextForToast = LocalContext.current.applicationContext

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
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = "$size (${category.name})"
                            onItemSelected(selectedItem)
                            expanded = false
                        }
                    ) {
                        Text(size)
                    }
                }
            }
        }
    }
}