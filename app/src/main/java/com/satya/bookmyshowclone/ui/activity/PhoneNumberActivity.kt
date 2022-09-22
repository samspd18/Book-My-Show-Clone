package com.satya.bookmyshowclone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satya.bookmyshowclone.R
import com.satya.bookmyshowclone.databinding.ActivityLoginBinding
import com.satya.bookmyshowclone.databinding.ActivityPhoneNumberBinding

class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //back button clicked
        backButtonCalled()

    }

    private fun backButtonCalled() {
        binding.back.setOnClickListener {
            this.onBackPressed()
        }
    }
}