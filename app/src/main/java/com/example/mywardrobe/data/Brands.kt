package com.example.mywardrobe.data

import android.content.Context
import android.util.Log

data class Brand(val name: String)

object BrandManager {
    private var brands: MutableList<Brand> = mutableListOf()

    fun initializeBrands() {
        Log.w("Brands", "In init method")
        // Liste hardcodée de marques
        val hardcodedBrands = listOf("Zara","H&M","Uniqlo","Monoprix","Mango","Nike", "Adidas", "Puma", "Levi's")
        brands.addAll(hardcodedBrands.map { Brand(it) })
    }

    fun loadBrands(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyWardrobePreferences", Context.MODE_PRIVATE)
        val brandsSet = sharedPreferences.getStringSet("brands", null)
        brandsSet?.let { brandsList ->
            // Fusionnez les marques enregistrées avec les marques hardcodées en évitant les doublons
            val brandsSetList = brandsList.map { Brand(it) }.toMutableList()
            brands = (brands + brandsSetList).distinct().toMutableList()
        }
    }

    fun getBrands() = brands.toList() // Retourner une copie immuable de la liste
    fun getBrandsString() = brands.map { it.name }.toList()

    fun addBrand(context: Context, brandName: String) {
        val newBrand = Brand(brandName)
        if (!brands.contains(newBrand)) {
            brands.add(newBrand)
            saveBrands(context)
        }
    }

    private fun saveBrands(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyWardrobePreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            val brandsSet = brands.map { it.name }.toSet()
            putStringSet("brands", brandsSet)
            apply()
        }
    }
}

