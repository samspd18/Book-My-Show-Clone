package com.satya.bookmyshowclone.viewModel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.bookmyshowclone.model.opt.GenerateOtp
import com.satya.bookmyshowclone.repositories.ApiCallRepositories
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel (private val repository: ApiCallRepositories): ViewModel() {

    val otp = MutableLiveData<GenerateOtp>()
    val errorMessage = MutableLiveData<String>()
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun getOtp(phone_number: String) {
        Log.e("phone_number", phone_number)
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val otpResponse = repository.generateOtp(phone_number)
            otpResponse.enqueue(object : Callback<GenerateOtp> {
                override fun onResponse(call: Call<GenerateOtp>, response: Response<GenerateOtp>) {
                   // apiCallFinished()
                    otp.postValue(response.body())
                }
                override fun onFailure(call: Call<GenerateOtp>, t: Throwable) {
                   // apiCallFinished()
                    errorMessage.postValue(t.message)
                }
            })
        }
    }
}