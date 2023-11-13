package com.example.mywardrobe

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
    val title: String,
    val type: ClotheType,
    val size: String,
    val brand: String,
    val desc: String = ""
)
