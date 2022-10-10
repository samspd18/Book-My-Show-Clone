package com.satya.bookmyshowclone.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.satya.bookmyshowclone.Adapter.CityAdapter
import com.satya.bookmyshowclone.constants.AppConstants
import com.satya.bookmyshowclone.constants.AppConstants.Companion.PERMISIISON_ID
import com.satya.bookmyshowclone.databinding.ActivityCityBinding
import com.satya.bookmyshowclone.model.city.City
import com.satya.bookmyshowclone.model.city.CityArray
import java.io.IOException
import java.util.*

class CityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0

    private val adapter = CityAdapter()
    lateinit var cities: Array<City>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //click of the detect location
        clickOfTheLocation()

        //city adapter
        binding.cityRecyclerView.adapter  = adapter
        cities = CityArray.categories
        adapter.setCities(cities)
        adapter.notifyDataSetChanged()
    }

    private fun clickOfTheLocation() {

        binding.locationCardView.setOnClickListener {
            //get the address
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
            if(checkPermissions()){
                if(isLocationEnabled()){
                    mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                        val location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData()
                        }else{
                            latitude = location.latitude
                            longitude = location.longitude

                            getAddress(latitude,longitude)
                        }
                    }
                }else{
                    Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
            else{
                requestPermissions()
            }
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            latitude = mLastLocation?.latitude!!
            longitude = mLastLocation.longitude

            getAddress(latitude,longitude)
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISIISON_ID
        )
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISIISON_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): String {
        val result = StringBuilder()
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                result.append(address.locality).append("\n")
                result.append(address.countryName)

                val sharedPreferences: SharedPreferences = this.getSharedPreferences(AppConstants.sharedPrefFile, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("city",address.locality)
                editor.apply()

                val intent = Intent(this, CityActivity::class.java)
                startActivity(intent)
                finish()

                Log.e("mainActivity", "Address :" + address.subLocality + "," + address.locality + ","
                        + address.subAdminArea + "," + address.adminArea+ "," + address.countryName + ","
                        + address.postalCode + "," + address.countryCode )
            }
        } catch (e: IOException) {
            Log.e("tag", e.message.toString())
        }
        return result.toString()
    }


}