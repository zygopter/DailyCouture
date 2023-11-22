package com.example.mywardrobe

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywardrobe.data.*
import com.example.mywardrobe.viewmodels.CatalogViewModel
import com.example.mywardrobe.viewmodels.WeatherViewModel
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: CatalogViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialisez la liste des marques au démarrage de l'application
        BrandManager.initializeBrands()
        BrandManager.loadBrands(this)
        StoragePlaceManager.initializeStoragePlaces()
        StoragePlaceManager.loadStoragePlaces(this)

        viewModel.saveClotheItem(ClotheItem(R.drawable.chemise_blanche, emptyList<Uri>(),"Chemise blanche élégante",
            ClothingCategory("Chemises"), Size("Vêtements","XL"),"H&M", "Armoire"))


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

    // Fonction fictive pour récupérer la liste des vêtements depuis une API ou une base de données
    fun getClotheItemsFromApi(): List<ClotheItem> {
        // Ici, vous pouvez implémenter la logique pour récupérer les vêtements depuis une source de données
        // Par exemple, vous pourriez effectuer une requête réseau à une API ou interagir avec une base de données locale
        // Pour l'exemple, créons une liste statique de vêtements fictifs
        val clotheItems: MutableList<ClotheItem> = mutableListOf()

        // Ajoutez des éléments à la liste des vêtements (c'est un exemple fictif, veuillez adapter cette logique à votre cas d'utilisation réel)
        //clotheItems.add(ClotheItem(R.drawable.chemise_blanche,"Chemise blanche élégante", ClotheType.TOPS, "XL", "H&M"))
        //clotheItems.add(ClotheItem(R.drawable.pantalon_noir,"Pantalon noir classique", ClotheType.PANTS, "US33", "Levi's"))
        //clotheItems.add(ClotheItem(R.drawable.robe_ete, "Robe d'été légère", ClotheType.DRESSES, "42", "Zalando"))
        // ... Ajoutez d'autres vêtements à la liste

        return clotheItems
    }
}
