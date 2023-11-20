package com.example.mywardrobe.data

data class ClothingCategory(
    val name: String,
    val subCategories: List<ClothingCategory> = emptyList()
)

// Exemple d'arbre de catégories basé sur une structure générique de site de mode
val subCategoriesSweats = listOf(
    ClothingCategory("Sweats & sweats à capuche"),
    ClothingCategory("Sweats"),
    ClothingCategory("Kimonos"),
    ClothingCategory("Cardigans"),
    ClothingCategory("Boléros"),
    ClothingCategory("Boléros"),
    ClothingCategory("Autres pull-overs")
)

val subCategoriesTShirts = listOf(
    ClothingCategory("T-shirts"),
    ClothingCategory("Chemises"),
    ClothingCategory("Blouses"),
    // Ajoutez d'autres sous-catégories ici
)

val clothingCategories = listOf(
    ClothingCategory(
        name = "Vêtements",
        subCategories = listOf(
            ClothingCategory("Sweats et sweats à capuche", subCategoriesSweats),
            ClothingCategory("Robes"),
            ClothingCategory("Hauts et t-shirts", subCategoriesTShirts),
            ClothingCategory("Pantalons et leggings"),
            ClothingCategory("Combinaisons et combishorts"),
            ClothingCategory("Lingerie et pyjamas"),
            ClothingCategory("Vêtements de sport"),
            ClothingCategory("Manteaux et vestes"),
            ClothingCategory("Blazers et tailleurs"),
            ClothingCategory("Jupes"),
            ClothingCategory("Jeans"),
            ClothingCategory("Shorts"),
            ClothingCategory("Maillots de bain"),
            ClothingCategory("Maternité"),
            ClothingCategory("Costumes et tenues particulières"),
            ClothingCategory("Autres")
        )
    ),
    ClothingCategory(
        name = "Chaussures",
        subCategories = listOf(
            ClothingCategory("Baskets"),
            ClothingCategory("Bottes"),
            ClothingCategory("Sandales"),
            // Ajoutez d'autres sous-catégories ici
        )
    ),
    ClothingCategory(
        name = "Sacs",
        subCategories = listOf(
            ClothingCategory("Sacs à main"),
            ClothingCategory("Sacs à bandoulière"),
            ClothingCategory("Sacs à dos"),
            // Ajoutez d'autres sous-catégories ici
        )
    ),
    ClothingCategory(
        name = "Accessoires",
        subCategories = listOf(
            ClothingCategory("Bijoux"),
            ClothingCategory("Ceintures"),
            ClothingCategory("Écharpes"),
            // Ajoutez d'autres sous-catégories ici
        )
    )
    // Ajoutez d'autres catégories principales ici
)

// Fonction pour trouver une catégorie par nom
fun findCategoryByName(categories: List<ClothingCategory>, name: String): ClothingCategory? {
    categories.forEach { category ->
        if (category.name == name) return category
        findCategoryByName(category.subCategories, name)?.let { return it }
    }
    return null
}

// Fonction pour récupérer toutes les catégories sous forme de liste plate
fun getAllCategories(categories: List<ClothingCategory>): List<ClothingCategory> {
    return categories.flatMap {
        listOf(it) + getAllCategories(it.subCategories)
    }
}

