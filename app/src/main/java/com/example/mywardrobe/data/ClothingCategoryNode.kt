package com.example.mywardrobe.data

data class ClothingCategory(
    val fullName: String,
    val displayName: String = fullName
) {}

data class ClothingCategoryNode(
    val name: String,
    val subCategories: List<ClothingCategoryNode> = emptyList()
)

// Exemple d'arbre de catégories basé sur une structure générique de site de mode
val subCategoriesSweats = listOf(
    ClothingCategoryNode("Sweats & sweats à capuche"),
    ClothingCategoryNode("Sweats"),
    ClothingCategoryNode("Kimonos"),
    ClothingCategoryNode("Cardigans"),
    ClothingCategoryNode("Boléros"),
    ClothingCategoryNode("Autres pull-overs")
)

val subCategoriesTShirts = listOf(
    ClothingCategoryNode("T-shirts"),
    ClothingCategoryNode("Chemises"),
    ClothingCategoryNode("Blouses"),
    // Ajoutez d'autres sous-catégories ici
)

val clothingCategories = listOf(
    ClothingCategoryNode(
        name = "Vêtements",
        subCategories = listOf(
            ClothingCategoryNode("Sweats et sweats à capuche", subCategoriesSweats),
            ClothingCategoryNode("Robes"),
            ClothingCategoryNode("Hauts et t-shirts", subCategoriesTShirts),
            ClothingCategoryNode("Pantalons et leggings"),
            ClothingCategoryNode("Combinaisons et combishorts"),
            ClothingCategoryNode("Lingerie et pyjamas"),
            ClothingCategoryNode("Vêtements de sport"),
            ClothingCategoryNode("Manteaux et vestes"),
            ClothingCategoryNode("Blazers et tailleurs"),
            ClothingCategoryNode("Jupes"),
            ClothingCategoryNode("Jeans"),
            ClothingCategoryNode("Shorts"),
            ClothingCategoryNode("Maillots de bain"),
            ClothingCategoryNode("Maternité"),
            ClothingCategoryNode("Costumes et tenues particulières"),
            ClothingCategoryNode("Autres")
        )
    ),
    ClothingCategoryNode(
        name = "Chaussures",
        subCategories = listOf(
            ClothingCategoryNode("Baskets"),
            ClothingCategoryNode("Bottes"),
            ClothingCategoryNode("Sandales"),
            // Ajoutez d'autres sous-catégories ici
        )
    ),
    ClothingCategoryNode(
        name = "Sacs",
        subCategories = listOf(
            ClothingCategoryNode("Sacs à main"),
            ClothingCategoryNode("Sacs à bandoulière"),
            ClothingCategoryNode("Sacs à dos"),
            // Ajoutez d'autres sous-catégories ici
        )
    ),
    ClothingCategoryNode(
        name = "Accessoires",
        subCategories = listOf(
            ClothingCategoryNode("Bijoux"),
            ClothingCategoryNode("Ceintures"),
            ClothingCategoryNode("Écharpes"),
            // Ajoutez d'autres sous-catégories ici
        )
    )
    // Ajoutez d'autres catégories principales ici
)

// Fonction pour trouver une catégorie par nom
fun findCategoryByName(categories: List<ClothingCategoryNode>, name: String): ClothingCategoryNode? {
    categories.forEach { category ->
        if (category.name == name) return category
        findCategoryByName(category.subCategories, name)?.let { return it }
    }
    return null
}

// Fonction pour récupérer toutes les catégories sous forme de liste plate
fun getAllCategories(categories: List<ClothingCategoryNode>): List<ClothingCategoryNode> {
    return categories.flatMap {
        listOf(it) + getAllCategories(it.subCategories)
    }
}

