package com.example.mywardrobe

enum class Style {
    CASUAL, FORMAL, WORK, SPORTWEAR // Ajoutez d'autres styles au besoin
}

data class Outfit(
    val clotheItems: List<ClotheItem>,
    val name: String,
    val pictureUrl: String,
    val style: Style // Ajout de l'attribut style
)
