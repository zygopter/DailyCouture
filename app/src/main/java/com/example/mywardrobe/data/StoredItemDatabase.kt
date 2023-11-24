package com.example.mywardrobe.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mywardrobe.dao.StoredItemDao
import com.example.mywardrobe.model.*

@Database(entities = [StoredItem::class], version = 1)
@TypeConverters(StringListTypeConverter::class ,UriListTypeConverter::class, SizeTypeConverter::class, ClothingCategoryTypeConverter::class)
abstract class StoredItemDatabase : RoomDatabase() {
    abstract fun itemDao(): StoredItemDao
}
