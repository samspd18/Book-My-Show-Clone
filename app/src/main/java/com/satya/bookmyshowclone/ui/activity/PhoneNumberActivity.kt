package com.satya.bookmyshowclone.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.satya.bookmyshowclone.constants.AppConstants
import com.satya.bookmyshowclone.databinding.ActivityPhoneNumberBinding
import com.satya.bookmyshowclone.network.RetrofitInstanceOtp
import com.satya.bookmyshowclone.repositories.ApiCallRepositories
import com.satya.bookmyshowclone.viewModel.ViewModel
import com.satya.bookmyshowclone.viewModel.ViewModelFactory

class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var viewModel: ViewModel
    private val retrofitService = RetrofitInstanceOtp.getInstance()
    private var getOtp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel Instance
        viewModel = ViewModelProvider(this, ViewModelFactory(ApiCallRepositories(retrofitService)))[ViewModel::class.java]
            //ViewModelProvider(this, ViewModelFactory(ApiCallRepositories(retrofitService)))[ViewModel::class.java]

        //generate otp
        generateOtp()
        //verifyOtpResponse
        getOtpResponse()
        //verify otp
        verifyOtpResponse()
        //saving data
        savingData()

        //back button clicked
        backButtonCalled()
    }

    private fun generateOtp() {
        binding.Continue.setOnClickListener {
            val numberLength = binding.PhoneNumberEditText.text.length
            val number = "+91"+binding.PhoneNumberEditText.text
            if(numberLength == 10 && isValidMobile(binding.PhoneNumberEditText.text.toString())) {
                viewModel.getOtp("+91${binding.PhoneNumberEditText.text}")
            } else {
                Toast.makeText(applicationContext, "Kindly check your phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getOtpResponse() {
        viewModel.otp.observe(this) {
            Log.e("otp", it.otp.toString())
            if(it.otp?.length != 0) {
                getOtp = it.otp.toString()
                binding.otpFullView.visibility = View.VISIBLE
                binding.otpVerify.visibility = View.VISIBLE
                binding.materialCardView7.visibility = View.GONE
                "Kindly Enter the OTP".also { binding.title1.text = it }
                val lastFourNumbers = binding.PhoneNumberEditText.text.substring(binding.PhoneNumberEditText.text.length - 4)
                "OTP send to number end with $lastFourNumbers".also { binding.title2.text = it }
            } else {
                binding.otpFullView.visibility = View.GONE
                binding.otpVerify.visibility = View.GONE
                binding.materialCardView7.visibility = View.VISIBLE
            }
        }
    }

    private fun verifyOtpResponse() {
        binding.otpVerify.setOnClickListener {
            val enteredOtp = binding.otp1.text.toString()+binding.otp2.text.toString()+binding.otp3.text.toString()+
                    binding.otp4.text.toString()+binding.otp5.text.toString()+binding.otp6.text.toString()

            if(enteredOtp == getOtp) {
                binding.otpFullView.visibility = View.GONE
                Toast.makeText(
                    application,
                    "Phone number verified successfully",
                    Toast.LENGTH_SHORT
                ).show()
                binding.title2.text = ""
                "Kindly enter your personal details".also { binding.title1.text = it }
                binding.otpVerify.visibility = View.GONE
                binding.Save.visibility = View.VISIBLE
                binding.nameTitle.visibility = View.VISIBLE
                binding.mailTitile.visibility = View.VISIBLE
                binding.nameCardView.visibility = View.VISIBLE
                binding.mailMaterialCardView.visibility = View.VISIBLE
            }
        }
    }

    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    private fun savingData() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(AppConstants.sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        
        binding.Save.setOnClickListener {
            val name = binding.nameEditText.text
            val mail = binding.mailEditText.text

            if(name.isNotEmpty() && mail.isNotEmpty()) {
                editor.putString("name", name.toString())
                editor.putString("email", mail.toString())
                editor.putString("profileUrl", "https://cdn-icons-png.flaticon.com/128/4140/4140048.png")

                editor.apply()

                val intent = Intent(this, CityActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(applicationContext, "Logged in as $name", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Kindly fill all the credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun backButtonCalled() {
        binding.back.setOnClickListener {
            this.onBackPressed()
        }
    }
}