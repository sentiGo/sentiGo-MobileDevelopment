package com.sentigo.bangkit.sentigoapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: AppRepository) : ViewModel() {

    val getUserPref = repo.getUserPref()
    val getUserResponse = repo.userResponse

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch {
            repo.deleteAllFavorite()
        }
    }

    fun getUser(token: String, id: Int) {
        viewModelScope.launch {
            repo.getUser(token, id)
        }
    }
}