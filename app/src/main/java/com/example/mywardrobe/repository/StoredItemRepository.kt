package com.example.mywardrobe.repository

import com.example.mywardrobe.dao.StoredItemDao
import com.example.mywardrobe.model.StoredItem
import kotlinx.coroutines.flow.Flow

class StoredItemRepository(private val storedItemDao: StoredItemDao) {
    // Récupérer tous les items
    fun getAllItems(): Flow<List<StoredItem>> {
        return storedItemDao.getAll()
    }

    fun getItemById(itemId: String): Flow<StoredItem?> {
        return storedItemDao.getItemById(itemId)
    }

    // Insérer un nouvel item
    suspend fun insertItem(item: StoredItem) {
        storedItemDao.insert(item)
    }

    // Supprimer un item
    suspend fun deleteItem(item: StoredItem) {
        storedItemDao.delete(item)
    }

    // Mettre à jour un item
    suspend fun updateItem(item: StoredItem) {
        storedItemDao.update(item)
    }
}