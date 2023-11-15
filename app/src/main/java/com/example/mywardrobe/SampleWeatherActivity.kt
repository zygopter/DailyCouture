package com.example.mywardrobe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mywardrobe.data.Location
import com.google.android.gms.location.FusedLocationProviderClient

private const val LOCATION_PERMISSION_REQUEST_CODE =34
/*
class SampleWeatherActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    var cityName: String = ""

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        CoroutineScope(Dispatchers.IO).launch {
            if (isNetworkAvailable())
                weatherApi()
        }
    }

    private suspend fun weatherApi() {
        var client: ApiInterface = RetrofitClient.service
        var weatherResponse = client.getCurrentWeatherAsync(cityName.ifEmpty {DEFAULT_LOCATION }).await()

        withContext(Dispatchers.Main) {
            with(binding) {
                temperatureTextView.text = weatherResponse.current?.temperature.toString()
                countryTextView.text = weatherResponse.location?.country.toString()
                Glide.with(this@MainActivity).load(weatherResponse.current?.weatherIcons?.get(0).toString()).into(weatherIconImageView)

                if (cityName.isNotEmpty()) {
                    cityTextView.text = cityName
                } else
                    cityTextView.text = DEFAULT_LOCATION
                weatherDescriptionTextView.text = weatherResponse.current?.weatherDescriptions?.get(0).toString()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        if(isNetworkAvailable())
                            setUpLocationListener()
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            setUpLocationListener()
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Permission not granted",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    currentLocation = locationResult.lastLocation

                    var lat = currentLocation!!.latitude
                    var long = currentLocation!!.longitude
                    val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(lat, long, 1)
                    cityName = addresses[0].getAddressLine(0)
                }
            },
            Looper.myLooper()!!
        )
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
*/