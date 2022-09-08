package com.satya.bookmyshowclone.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satya.bookmyshowclone.R
import com.satya.bookmyshowclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change the marketing text
        changMarketingText()

        //go to phone number page
        goToPhoneNumberActivity()

    }

    private fun changMarketingText() {
        var randomIndex =  0
        val handler = Handler(Looper.getMainLooper())
        val updateTask: Runnable = object : Runnable {
            override fun run() {
                if (randomIndex == 0) {
                    randomIndex = 1
                } else if(randomIndex == 1) {
                    randomIndex = 2
                } else if(randomIndex == 2) {
                    randomIndex = 0
                }
                binding.marketingText.text = resources.getStringArray(R.array.marketingArray)[randomIndex]
                handler.postDelayed(this, 3500)
            }
        }

        handler.postDelayed(updateTask, 3500)
    }

    private fun goToPhoneNumberActivity() {
        binding.PhoneNumberEditText.setOnClickListener {
            val intent = Intent(this, PhoneNumberActivity::class.java)
            startActivity(intent)
        }
    }

}