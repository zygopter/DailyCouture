package com.example.mywardrobe

import com.example.mywardrobe.data.Brand
import com.example.mywardrobe.data.ClothingCategory
import com.example.mywardrobe.data.SizeCategory
import com.example.mywardrobe.data.StoragePlace

enum class ClotheType(val displayName: String) {
    TOPS("Tops"),
    PANTS("Pants"),
    DRESSES("Dresses"),
    JEANS("Jeans"),
    FOOTWEAR("Shoes"),
    ACCESSORIES("Accessories"),
    SWIMWEAR("Swimwear"),
    UNDERWEAR("Underwear"),
    SLEEPWEAR("Sleepwear"),
    SPORTSWEAR("sportwear"),
    COSTUMES("Costumes"),
    MATERNITYWEAR("Maternitywear"),
    PARTYWEAR("Partywear"),
    OTHER("Other")
}

data class ClotheItem(
    val pictureRes: Int,
    val pictures: List<Int>,
    val title: String,
    val category: ClothingCategory,
    val size: String,
    val brand: Brand,
    val storedPlace: StoragePlace,
    val notes: String = ""
)
