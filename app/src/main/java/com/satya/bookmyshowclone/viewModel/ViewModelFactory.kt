package com.satya.bookmyshowclone.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satya.bookmyshowclone.repositories.ApiCallRepositories

class ViewModelFactory constructor(private val repository: ApiCallRepositories) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(com.satya.bookmyshowclone.viewModel.ViewModel::class.java)) {
            ViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}