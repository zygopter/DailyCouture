package com.example.mywardrobe.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mywardrobe.ClotheItem
import com.example.mywardrobe.R
import com.example.mywardrobe.data.Brand
import com.example.mywardrobe.data.ClothingCategory
import com.example.mywardrobe.data.ClothingCategoryNode
import com.example.mywardrobe.data.StoragePlace

class CatalogViewModel: ViewModel() {
    private val _clotheItems = mutableStateListOf<ClotheItem>()
    val clotheItems: List<ClotheItem> get() = _clotheItems

    // État pour gérer l'ouverture/fermeture de la boîte de dialogue
    var isDialogOpen by mutableStateOf(false)

    // Fonction pour enregistrer le nouvel objet ClotheItem
    fun saveClotheItem(item: ClotheItem) {
        // Logique pour enregistrer l'objet ClotheItem dans votre base de données ou votre repository
        // Réinitialisez la boîte de dialogue après l'enregistrement
        println("in saveClotheItem method")
        _clotheItems.add(item)
        isDialogOpen = false
    }
}