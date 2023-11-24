package com.example.mywardrobe.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mywardrobe.data.*
import com.example.mywardrobe.model.StoredItem
import com.example.mywardrobe.repository.StoredItemRepository
import kotlinx.coroutines.launch

class CatalogViewModel(private val repository: StoredItemRepository): ViewModel() {
    // LiveData pour observer les changements dans la liste des items
    val storedItems: LiveData<List<StoredItem>> = repository.getAllItems().asLiveData()

    // Fonction pour enregistrer le nouvel objet ClotheItem
    fun saveClotheItem(item: ClotheItem) {
        println("in saveClotheItem method")
        val storedItem = StoredItem(pictures = item.pictures,
            picturePaths = item.picturePaths,
            title = item.title,
            category = item.category,
            size = item.size,
            brand = item.brand,
            storedPlace = item.storedPlace
        )
        viewModelScope.launch {
            repository.insertItem(storedItem)
        }
    }

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            repository.getAllItems()
        }
    }

}

class CatalogViewModelFactory(private val repository: StoredItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
