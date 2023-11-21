package com.example.mywardrobe

import com.example.mywardrobe.data.*

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
    val size: Size,
    val brand: String,
    val storedPlace: String,
    val notes: String = ""
)
