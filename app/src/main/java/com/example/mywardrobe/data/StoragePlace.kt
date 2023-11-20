package com.example.mywardrobe.data

import android.content.Context
import android.util.Log
import com.example.mywardrobe.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class StoragePlace(val name: String, val iconResource: Int)

object StoragePlaceManager {
    private var storagePlaces: MutableList<StoragePlace> = mutableListOf()

    fun initializeStoragePlaces() {
        Log.w("StoragePlaceManager", "In init method")
        // Liste hardcodée de storage
        val storagePlaceSamples = mapOf<String,Int>("Armoire" to R.drawable.wardrobe_icon,
        "Armoire carrée" to R.drawable.closet_icon,
        "Commode" to R.drawable.drawer_furniture,
        "Penderie" to R.drawable.dressing)
        storagePlaces.addAll(storagePlaceSamples.map { StoragePlace(it.key,it.value) })
    }

    fun loadStoragePlaces(context: Context) {
        val jsonString = context.getSharedPreferences("MyWardrobePreferences", Context.MODE_PRIVATE).getString("storagePlaces", null)
        if (jsonString != null) {
            val type = object : TypeToken<Map<String, Int>>() {}.type
            val storagePlacesSetList: Map<String, Int> = Gson().fromJson(jsonString, type)
            storagePlacesSetList.forEach { (key, value) ->
                storagePlaces = (storagePlaces + StoragePlace(key, value)).distinct().toMutableList()
            }
        }
    }

    fun getStoragePlaces() = storagePlaces.toList() // Retourner une copie immuable de la liste
    fun getStoragePlacesAsMap(): Map<String,Int> {
        val storagePlacesAsMap = emptyMap<String,Int>()
        storagePlaces.forEach { it ->
            storagePlacesAsMap.plus(Pair(it.name,it.iconResource))
        }
        return storagePlacesAsMap
    }
    fun getStoragePlacesString() = storagePlaces.map { it.name }.toList()

    fun addStoragePlaces(context: Context, storagePlaceName: String) {
        val newStoragePlace = StoragePlace(storagePlaceName, R.drawable.wardrobe_icon)
        if (!storagePlaces.contains(newStoragePlace)) {
            storagePlaces.add(newStoragePlace)
            saveStoragePlaces(context)
        }
    }

    private fun saveStoragePlaces(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyWardrobePreferences", Context.MODE_PRIVATE)
        val jsonString = Gson().toJson(storagePlaces)
        with(sharedPreferences.edit()) {
            putString("storagePlaces", jsonString)
            apply()
        }
    }
}

