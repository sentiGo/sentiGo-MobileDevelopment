package com.sentigo.bangkit.sentigoapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.sentigo.bangkit.sentigoapp.data.AppRepository

class FavoriteViewModel(private val repo: AppRepository) : ViewModel() {

    fun getFavoriteDb() = repo.getFavoriteDb()
}