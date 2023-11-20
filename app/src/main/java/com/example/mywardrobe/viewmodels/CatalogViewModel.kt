package com.example.mywardrobe.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mywardrobe.ClotheItem
import com.example.mywardrobe.ClotheType
import com.example.mywardrobe.R
import com.example.mywardrobe.data.Brand
import com.example.mywardrobe.data.ClothingCategory
import com.example.mywardrobe.data.StoragePlace

class CatalogViewModel: ViewModel() {
    private val _clotheItems = mutableStateListOf<ClotheItem>()
    val clotheItems: List<ClotheItem> get() = _clotheItems

    // État pour gérer l'ouverture/fermeture de la boîte de dialogue
    var isDialogOpen by mutableStateOf(false)

    // État pour gérer les données du nouvel objet ClotheItem
    var newClotheItem by mutableStateOf(ClotheItem(R.drawable.placeholder_image, emptyList<Int>(), "", ClothingCategory(""), "", Brand(""), StoragePlace("", R.drawable.wardrobe_icon)))
        private set

    // Fonction pour ouvrir la boîte de dialogue
    fun openDialog() {
        isDialogOpen = true
        // Réinitialisez les données du nouvel objet ClotheItem si nécessaire
        newClotheItem = ClotheItem(R.drawable.placeholder_image, emptyList<Int>(),"", ClothingCategory(""), "", Brand(""), StoragePlace("", R.drawable.wardrobe_icon))
    }

    fun updateNewClotheItem(item: ClotheItem) {
        newClotheItem = item
    }

    // Fonction pour enregistrer le nouvel objet ClotheItem
    fun saveClotheItem(item: ClotheItem) {
        // Logique pour enregistrer l'objet ClotheItem dans votre base de données ou votre repository
        // Réinitialisez la boîte de dialogue après l'enregistrement
        println("in saveClotheItem method")
        _clotheItems.add(item)
        isDialogOpen = false
    }
}