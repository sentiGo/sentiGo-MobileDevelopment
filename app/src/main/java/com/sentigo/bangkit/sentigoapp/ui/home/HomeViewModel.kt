package com.sentigo.bangkit.sentigoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: AppRepository) : ViewModel() {

    val listRatingDestinasi: LiveData<Result<List<ListDestinasiItem>>> = repo.listHomeDestinasi

    val getUser = repo.getUserPref()

    fun getListRatingDestinasi(token: String) {
        viewModelScope.launch {
            repo.getListRatingDestinasi(token)
        }
    }

    fun setLocationPref(lat: Float, lon: Float) {
        viewModelScope.launch {
            repo.setLocationPref(lat, lon)
        }
    }
}