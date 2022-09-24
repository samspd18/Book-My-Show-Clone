package com.satya.bookmyshowclone.model.opt


import com.google.gson.annotations.SerializedName

data class GenerateOtp(
    @SerializedName("Details")
    val details: String?,
    @SerializedName("OTP")
    val otp: String?,
    @SerializedName("Status")
    val status: String?
)