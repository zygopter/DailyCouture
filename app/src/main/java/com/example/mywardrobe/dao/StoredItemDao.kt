package com.example.mywardrobe.dao

import androidx.room.*
import com.example.mywardrobe.model.StoredItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredItemDao {
    @Query("SELECT * FROM storedItem")
    fun getAll(): Flow<List<StoredItem>>

    @Query("SELECT * FROM storedItem WHERE storedItem.uuid LIKE :itemId")
    fun getItemById(itemId: String): Flow<StoredItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: StoredItem)

    @Update
    suspend fun update(item: StoredItem)

    @Delete
    suspend fun delete(item: StoredItem)
}
