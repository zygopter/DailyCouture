package com.example.mywardrobe.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
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

    fun getItemByIdWithoutQuery(itemId: String): StoredItem? {
        storedItems.value?.forEach { item ->
            if (item.uuid == itemId) return item
        }
        return null
    }

    fun getItemById(itemId: String): LiveData<StoredItem?> = liveData {
        emitSource(repository.getItemById(itemId).asLiveData())
    }

    fun updateStoredItem(storedItem: StoredItem) {
        viewModelScope.launch {
            repository.updateItem(storedItem)
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
