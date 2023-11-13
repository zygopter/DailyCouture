package com.example.mywardrobe.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableListSelector(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    onAddItem: (String) -> Unit,
    label: String,
    addLabel: String = "Add new..."
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    //Column {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                expanded = it.isNotBlank()
                textFieldValue = it },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused && textFieldValue.isNotBlank()
                },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    Modifier.clickable { expanded = !expanded }
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus() // Cache le clavier quand l'utilisateur confirme avec 'Done'
                expanded = false
            })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            /*Column(modifier = Modifier.verticalScroll(rememberScrollState()).height(200.dp)) {
                items.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onItemSelected(item)
                        expanded = false
                        textFieldValue = item // Set the text field value to the selected item
                    }) {
                        Text(text = item)
                    }
                }
                DropdownMenuItem(onClick = {
                    onAddItem(textFieldValue)
                    expanded = false
                    //textFieldValue = "" // Clear the text field after adding the item
                }) {
                    Text(text = "Add '$textFieldValue'")
                }
            }*/
            DropdownMenuItem(onClick = {
                expanded = false
                onAddItem(textFieldValue)
                //textFieldValue = "..."
            }) {
                Text("Add $textFieldValue")
            }
            Divider()
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                    textFieldValue = item
                }) {
                    Log.w("Expandable list", "Item: $item")
                    Text(item)
                }
            }
        }
    //}
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@Composable
fun MyExposedDropdownMenu(items: List<String>,
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
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedItem,
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
