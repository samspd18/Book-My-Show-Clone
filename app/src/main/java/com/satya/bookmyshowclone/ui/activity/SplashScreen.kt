package com.satya.bookmyshowclone.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.satya.bookmyshowclone.R
import com.satya.bookmyshowclone.constants.AppConstants.Companion.sharedPrefFile
import com.satya.bookmyshowclone.ui.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","")!!
        val city = sharedPreferences.getString("city","")!!

        Log.e("shared", "$name-$city")

        //navigation to the application

        if(name.isNotEmpty() && city.isEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, CityActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        } else if (name.isEmpty() && city.isEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        else if(name.isNotEmpty() && city.isNotEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}