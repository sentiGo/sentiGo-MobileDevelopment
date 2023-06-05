package com.sentigo.bangkit.sentigoapp.ui.profile.sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.RegisterResponse
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProfilePhotoViewModel(private val repo: AppRepository) : ViewModel() {

    val getUserPref = repo.getUserPref()
    val updatePhotoResponse : LiveData<Result<RegisterResponse>> get() = repo.updatePhotoResponse

    fun updatePhotoProfile(token: String, photo: MultipartBody.Part) {
        viewModelScope.launch {
            repo.updatePhotoProfile(token, photo)
        }
    }
}