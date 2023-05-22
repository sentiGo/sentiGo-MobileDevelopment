package com.sentigo.bangkit.sentigoapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.LoginResult
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: AppRepository) : ViewModel() {

    val loginResponse: LiveData<Result<LoginResult>> get() = repo.loginResponse

    fun userLogin(email: String, password: String) {
        viewModelScope.launch {
            repo.loginUser(email, password)
        }
    }
}