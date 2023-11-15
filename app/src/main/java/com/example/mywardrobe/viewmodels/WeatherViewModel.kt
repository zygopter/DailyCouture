package com.example.mywardrobe.viewmodels

import android.location.Address
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywardrobe.data.WeatherResponse
import com.example.mywardrobe.network.RetrofitClient
import com.example.mywardrobe.utils.PermissionUtils.DEFAULT_LOCATION
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData
    private val _weatherIcon = MutableLiveData<ImageVector>()
    val weatherIcon: LiveData<ImageVector> = _weatherIcon

    // Fonction pour lancer une requête météo avec une localisation donnée
    fun getWeatherForLocation(cityName: String) {
        viewModelScope.launch {
            try {
                // Remplacez 'ApiInterface::class.java' par votre interface de service Retrofit
                val weatherService = RetrofitClient.service
                // Faites l'appel réseau et postez le résultat dans le LiveData
                val response = weatherService.getCurrentWeatherAsync(cityName.ifEmpty {DEFAULT_LOCATION })
                _weatherData.postValue(response)
            } catch (e: Exception) {
                // Gérer l'exception si l'appel réseau échoue
                e.printStackTrace()
            }
        }
    }

    // Exemple de méthode pour obtenir la météo - à appeler après avoir obtenu la localisation
    /*fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = weatherApiService.getCurrentWeather(latitude, longitude, "your_api_key")
                val iconCode = response.weather.firstOrNull()?.icon
                // Convertissez iconCode en ImageVector ou utilisez une image correspondante dans votre projet
                _weatherIcon.value = // Votre logique de conversion ici
            } catch (e: Exception) {
                // Gérez les erreurs de réseau ici
            }
        }
    }*/
}