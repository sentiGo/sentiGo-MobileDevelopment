package com.sentigo.bangkit.sentigoapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.local.entity.FavoriteEntity
import com.sentigo.bangkit.sentigoapp.data.remote.response.DetailDestinasi
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: AppRepository) : ViewModel() {

    val getDetailDestinasiResponse: LiveData<Result<DetailDestinasi>> get() = repo.detailDestinasiResponse
    val getUserPref = repo.getUserPref()
    fun getFavoriteDb() = repo.getFavoriteDb()

    fun getDetailDestinasi(token: String, id_destinasi: Int) {
        viewModelScope.launch {
            repo.getDestinasiDetail(token, id_destinasi)
        }
    }

    fun saveFavoriteDb(data: FavoriteEntity) {
        viewModelScope.launch {
            repo.saveFavoriteDb(data)
        }
    }

    fun deleteFavoriteDb(id: Int) {
        viewModelScope.launch {
            repo.deleteFavoriteDb(id)
        }
    }
}