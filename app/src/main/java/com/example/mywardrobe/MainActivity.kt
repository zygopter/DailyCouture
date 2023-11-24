package com.example.mywardrobe

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mywardrobe.data.*
import com.example.mywardrobe.repository.StoredItemRepository
import com.example.mywardrobe.viewmodels.CatalogViewModel
import com.example.mywardrobe.viewmodels.CatalogViewModelFactory
import com.example.mywardrobe.viewmodels.WeatherViewModel
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: CatalogViewModel

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données Room
        val db = Room.databaseBuilder(
            applicationContext,
            StoredItemDatabase::class.java, "name-of-database"
        ).build()

        // Obtention de StoredItemDao
        val storedItemDao = db.itemDao()

        // Initialisation de StoredItemRepository
        val repository = StoredItemRepository(storedItemDao)
        viewModel = ViewModelProvider(this, CatalogViewModelFactory(repository)).get(CatalogViewModel::class.java)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialisez la liste des marques au démarrage de l'application
        BrandManager.initializeBrands()
        BrandManager.loadBrands(this)
        StoragePlaceManager.initializeStoragePlaces()
        StoragePlaceManager.loadStoragePlaces(this)

        /*viewModel.saveClotheItem(ClotheItem(R.drawable.chemise_blanche, emptyList<Uri>(),"Chemise blanche élégante",
            ClothingCategory("Chemises"), Size("Vêtements","XL"),"H&M", "Armoire"))*/


        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "wardrobeScreen") {
                composable("wardrobeScreen") { WardrobeScreen(viewModel, navController) }
                composable("qrCodeScannerScreen") { QRCodeScannerMLKit(navController, onCodeScanned = {}) }
                composable("createOutfitScreen") { CreateOutfitScreen(navController) }
                composable("addNewItemScreen") { NewItemScreen(navController = navController, viewModel) }
                composable("dailyPlannerScreen") { DailyPlannerScreen(navController = navController, weatherViewModel)}
            }
        }
    }
}
