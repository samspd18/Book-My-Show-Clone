package com.satya.bookmyshowclone.network

import com.satya.bookmyshowclone.model.opt.GenerateOtp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    //get the otp
    @GET("{phone_number}/AUTOGEN2/OTP1")
    fun generateOtp(@Path("phone_number") phone_number: String): Call<GenerateOtp>
}