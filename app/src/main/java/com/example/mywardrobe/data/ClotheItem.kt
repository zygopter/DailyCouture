package com.example.mywardrobe.data

import android.net.Uri

data class ClotheItem(
    val pictures: List<Uri>,
    val picturePaths: List<String>,
    val title: String,
    val category: ClothingCategory,
    val size: Size,
    val brand: String,
    val storedPlace: String,
    val notes: String = ""
)
