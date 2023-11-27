package com.example.mywardrobe.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mywardrobe.R

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun ExpandableListSelector(items: List<String>,
                           preselectedItem: String,
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
    selectedItem = preselectedItem

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
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { selectedItem = it },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // filter options based on text field value
        val filteringOptions =
            items.filter { it.contains(selectedItem, ignoreCase = true) }

        if (filteringOptions.isNotEmpty()) {
            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onAddItem(selectedItem)
                    //textFieldValue = "..."
                }) {
                    Text("Add $selectedItem")
                }
                Divider()
                // this is a column scope
                // all the items are added vertically
                filteringOptions.forEach { selectionOption ->
                    // menu item
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = selectionOption
                            onItemSelected(selectedItem)
                            Toast.makeText(contextForToast, selectedItem, Toast.LENGTH_SHORT).show()
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun ExpandableListWithIconSelector(items: Map<String,Int>,
                                   preselectedItem: String,
                           onItemSelected: (String) -> Unit,
                           onAddItem: (String) -> Unit,
                           label: String) {
    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf("")
    }
    selectedItem = preselectedItem
    var selectedIcon by remember {
        mutableStateOf(R.drawable.ic_armoire)
    }
    selectedIcon = items[selectedItem] ?: R.drawable.ic_armoire


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
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { selectedItem = it },
            label = { Text(label) },
            leadingIcon = {
                Icon(painter = painterResource(id = selectedIcon), contentDescription = "Storage icon")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // filter options based on text field value
        val filteringOptions =
            items.filter { it.key.contains(selectedItem, ignoreCase = true) }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onAddItem(selectedItem)
            }) {
                Text("Add $selectedItem")
            }
            Divider()
            // this is a column scope
            // all the items are added vertically
            if (filteringOptions.isNotEmpty()) {
                filteringOptions.forEach { selectionOption ->
                    // menu item
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = selectionOption.key
                            onItemSelected(selectedItem)
                            expanded = false
                        }
                    ) {
                        Icon(painter = painterResource(id = selectionOption.value), contentDescription = "Storage icon",
                            modifier = Modifier.padding(end = 16.dp))
                        Text(text = selectionOption.key)
                    }
                }
            } else {
                items.forEach { selectionOption ->
                    // menu item
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = selectionOption.key
                            onItemSelected(selectedItem)
                            expanded = false
                        }
                    ) {
                        Icon(painter = painterResource(id = selectionOption.value), contentDescription = "Storage icon")
                        Text(text = selectionOption.key)
                    }
                }
            }
        }
    }
}