package com.sentigo.bangkit.sentigoapp.ui.profile.sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.RegisterResponse
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch

class ChangePasswordSheetViewModel(private val repo: AppRepository) : ViewModel() {

    val changePasswordResponse : LiveData<Result<RegisterResponse>> get() = repo.changePasswordUserResponse
    val getUserPref = repo.getUserPref()

    fun putChangePassword(token: String, id: Int, oldPass: String?, newPass: String?) {
        viewModelScope.launch {
            repo.putChangePassword(token, id, oldPass, newPass)
        }
    }
}