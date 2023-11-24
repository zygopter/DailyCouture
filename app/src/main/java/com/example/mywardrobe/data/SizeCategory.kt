package com.example.mywardrobe.data

data class Size(
    val category: String,
    val size: String
) {
    companion object {
        fun toSize(sizeString: String): Size {
            return Size(sizeString.split("/").first(),
                sizeString.split("/").last())
        }
    }
}

data class SizeCategory(
    val name: String,
    val sizes: List<String>
)

// Tailles pour les vêtements
val clothingSizes = SizeCategory(
    "Vêtements",
    listOf("XS", "S", "M", "L", "XL", "XXL")
)

// Tailles pour les chaussures
val shoeSizes = SizeCategory(
    "Chaussures",
    listOf("36", "37", "38", "39", "40", "41", "42")
)

// Tailles pour les sous-vêtements
val underwearSizes = SizeCategory(
    "Sous-vêtements",
    listOf("S", "M", "L", "XL")
)

// Hiérarchie complète des tailles
val sizeHierarchy = listOf(clothingSizes, shoeSizes, underwearSizes)

