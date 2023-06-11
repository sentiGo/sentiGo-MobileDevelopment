package com.sentigo.bangkit.sentigoapp.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import kotlinx.coroutines.launch

class CityViewModel(private val repo: AppRepository) : ViewModel() {

    val getUserPref = repo.getUserPref()
    val listCityResponse: LiveData<Result<List<ListDestinasiItem>>> get() = repo.listCityResponse

    fun getCityList(token: String, city: String) {
        viewModelScope.launch {
            repo.getListCity(token, city)
        }
    }
}