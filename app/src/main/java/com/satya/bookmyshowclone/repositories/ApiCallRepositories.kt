package com.satya.bookmyshowclone.repositories

import com.satya.bookmyshowclone.network.RetrofitService

class ApiCallRepositories constructor(private val retrofitService: RetrofitService) {
    fun generateOtp(phone_number: String) = retrofitService.generateOtp(phone_number)
}