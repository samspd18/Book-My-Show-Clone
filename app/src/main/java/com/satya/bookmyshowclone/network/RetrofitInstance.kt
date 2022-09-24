package com.satya.bookmyshowclone.network

import com.satya.bookmyshowclone.constants.AppConstants.Companion.movieBaseUrl
import com.satya.bookmyshowclone.constants.AppConstants.Companion.twoFactorBaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceOtp {

    companion object {
        private const val otpBaseUrl = twoFactorBaseUrl
        var retrofitService: RetrofitService? = null

        //creating retrofit instance
        fun getInstance(): RetrofitService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(otpBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)
            }

            return retrofitService!!
        }
    }
}

class RetrofitInstance {

    companion object {
        private const val otpBaseUrl = movieBaseUrl
        var retrofitService: RetrofitService? = null

        //creating retrofit instance
        fun getInstance(): RetrofitService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(otpBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)
            }

            return retrofitService!!
        }
    }
}