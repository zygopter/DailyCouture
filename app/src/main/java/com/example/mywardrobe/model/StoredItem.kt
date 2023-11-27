package com.example.mywardrobe.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mywardrobe.data.ClothingCategory
import com.example.mywardrobe.data.Size
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

@Entity
data class StoredItem(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    val pictures: List<Uri>,
    val picturePaths: List<String>,
    val title: String,
    val category: ClothingCategory,
    val size: Size,
    val brand: String,
    val storedPlace: String,
    val notes: String = ""
)

class StringListTypeConverter {
    @TypeConverter
    fun fromStringList(paths: List<String>): String {
        return Gson().toJson(paths)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }
}

class UriListTypeConverter {
    @TypeConverter
    fun fromUriList(uris: List<Uri>): String {
        return Gson().toJson(uris.map { it.toString() }) // Convertit chaque Uri en String
    }

    @TypeConverter
    fun toUriList(uriString: String): List<Uri> {
        val type = object : TypeToken<List<String>>() {}.type
        val uriStrings: List<String> = Gson().fromJson(uriString, type)
        return uriStrings.map { Uri.parse(it) } // Convertit chaque String en Uri
    }
}

class SizeTypeConverter {
    @TypeConverter
    fun fromSize(size: Size): String {
        return "${size.category}/${size.size}"
    }

    @TypeConverter
    fun toSize(sizeString: String): Size {
        // Convertir un String en Size (peut nécessiter une logique personnalisée en fonction de la classe Size)
        return Size.toSize(sizeString)
    }
}

class ClothingCategoryTypeConverter {
    @TypeConverter
    fun fromClothingCategory(category: ClothingCategory): String {
        // Convertir ClothingCategory en String
        return category.fullName
    }

    @TypeConverter
    fun toClothingCategory(categoryString: String): ClothingCategory {
        // Convertir un String en ClothingCategory
        return ClothingCategory(categoryString, categoryString)
    }
}