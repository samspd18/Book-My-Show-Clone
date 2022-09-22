package com.satya.bookmyshowclone.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.satya.bookmyshowclone.R
import com.satya.bookmyshowclone.constants.AppConstants
import com.satya.bookmyshowclone.constants.AppConstants.Companion.sharedPrefFile
import com.satya.bookmyshowclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val RC_SIGN_IN = AppConstants.RC_SIGN_IN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change the marketing text
        changMarketingText()

        //go to phone number page
        goToPhoneNumberActivity()

        //disable the edit text
        binding.PhoneNumberEditText.showSoftInputOnFocus = false

        //google login
        googleLogin()
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

    private fun googleLogin(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

       // val account = GoogleSignIn.getLastSignedInAccount(this)

        binding.googleCardView.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            Toast.makeText(applicationContext,"Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()

            if(account != null) {
                editor.putString("name",account.displayName)
                editor.putString("email",account.email)
                editor.putString("profileUrl",account.photoUrl.toString())

                editor.apply()

//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()

                Log.e("Google", account.email.toString())
            } else {
                Toast.makeText(applicationContext, "Login Failed , Try again ", Toast.LENGTH_SHORT).show()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                mGoogleSignInClient.signOut()
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(applicationContext, "Some thing went wrong.", Toast.LENGTH_SHORT).show()
            //updateUI(null)
        }
    }

}