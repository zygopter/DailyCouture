package com.example.mywardrobe

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mywardrobe.components.WardrobeBottomNavigation
import com.example.mywardrobe.data.WeatherResponse
import com.example.mywardrobe.viewmodels.WeatherViewModel
import com.google.android.gms.location.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter


@Composable
fun DailyPlannerScreen(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Daily", "Agenda")

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // La permission est accordée, nous pouvons maintenant écouter la localisation
                getCurrentLocation(fusedLocationClient) { location ->
                    // Utilisez la localisation pour obtenir les données météo
                    val lat = location.latitude
                    val long = location.longitude
                    val geocoder = Geocoder(context, Locale.getDefault())
                    if (Build.VERSION.SDK_INT >= 33) {
                        val geocodeListener = Geocoder.GeocodeListener { addresses ->
                            weatherViewModel.getWeatherForLocation(addresses[0].getAddressLine(0))
                        }
                        geocoder.getFromLocation(lat, long, 1, geocodeListener)
                    } else {
                        val addresses = geocoder.getFromLocation(lat, long, 1)
                        addresses?.get(0)?.let { weatherViewModel.getWeatherForLocation(it.getAddressLine(0)) }
                    }
                }
            } else {
                // La permission a été refusée, gérer ce cas
            }
        }
    )

    // Vérifiez si la permission de localisation est déjà accordée
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // La permission est déjà accordée
                getCurrentLocation(fusedLocationClient) { location ->
                    // Utilisez la localisation pour obtenir les données météo
                    val lat = location.latitude
                    val long = location.longitude
                    val geocoder = Geocoder(context, Locale.getDefault())
                    if (Build.VERSION.SDK_INT >= 33) {
                        val geocodeListener = Geocoder.GeocodeListener { addresses ->
                            weatherViewModel.getWeatherForLocation(addresses[0].getAddressLine(0))
                        }
                        geocoder.getFromLocation(lat, long, 1, geocodeListener)
                    } else {
                        val addresses = geocoder.getFromLocation(lat, long, 1)
                        addresses?.get(0)?.let { weatherViewModel.getWeatherForLocation(it.getAddressLine(0)) }
                    }
                }
            }
            else -> {
                // La permission n'est pas accordée, demandez-la
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // Observez le LiveData pour les données météo du ViewModel
    val weatherData = weatherViewModel.weatherData.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Daily Planner") },
                // Ajoutez ici les actions si nécessaire
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Handle click */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            WardrobeBottomNavigation(0, navController)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
            if (selectedTab == 0) {
                // Daily tab content
                DailyContent(weatherData)
            } else {
                // Agenda tab content
            }
        }
    }
}

@Composable
fun DailyContent(weatherData: State<WeatherResponse?>) {
    val outfits = remember { mutableStateMapOf<LocalDate, String>() }
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    val today = LocalDate.now()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
            //verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentDate = currentDate.minusDays(1) }) {
                Icon(Icons.Filled.ArrowBack, "Previous Day")
            }

            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("EEEE d MMMM")),
                style = MaterialTheme.typography.h6
            )

            IconButton(
                onClick = { if (currentDate.isBefore(today)) currentDate = currentDate.plusDays(1) },
                enabled = currentDate.isBefore(today)
            ) {
                Icon(Icons.Filled.ArrowForward, "Next Day")
            }
        }
        Icon(
            painter = painterResource(R.drawable.baseline_wb_sunny_24),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(48.dp)
        )
        // Affichez les données météo si elles sont disponibles
        weatherData.value?.let { weather ->
            // Remplacez les "Text" par vos propres composants qui affichent les données
            Text(text = "Temperature: ${weather.current?.temperature}")
            Text(text = "Lieu: ${weather.location?.name}")
            Text(text = "Weather Description: ${weather.current?.weatherDescriptions}")
            val imagePainter: Painter = rememberImagePainter(weather.current?.weatherIcons?.get(0).toString())
            Image(
                painter = imagePainter,
                contentDescription = "Image loaded from URL"
            )
            // Ajoutez ici d'autres informations météo que vous souhaitez afficher
        } ?: run {
            // Affichez un message ou un indicateur de chargement si les données météo ne sont pas encore chargées
            Text(text = "Loading weather data...")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Start creating your daily outfit!")
        // Afficher la tenue ou proposer de créer une nouvelle
        if (outfits.containsKey(currentDate)) {
            Text("Your outfit for today: ${outfits[currentDate]}")
        } else {
            Button(onClick = { /* Logique pour créer une tenue */ }) {
                Text("Create your daily outfit")
            }
        }
    }
}

// Cette fonction utilise FusedLocationProviderClient pour obtenir la localisation actuelle
fun getCurrentLocation(fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (Location) -> Unit) {
    val locationRequest = LocationRequest.create().apply {
        interval = 2000
        fastestInterval = 2000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0 ?: return
            onLocationReceived(p0.lastLocation)
            // Arrêtez de demander des mises à jour de la localisation si nécessaire
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    // N'oubliez pas de vérifier les permissions ici aussi
    try {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } catch (e: SecurityException) {
        Log.e("DailyPlannerScreen", "Got a security exception: $e")
    }
}

@Preview(showBackground = true)
@Composable
fun DailyPlannerScreenPreview() {
    val navController = rememberNavController()
    DailyPlannerScreen(navController, WeatherViewModel())
}
