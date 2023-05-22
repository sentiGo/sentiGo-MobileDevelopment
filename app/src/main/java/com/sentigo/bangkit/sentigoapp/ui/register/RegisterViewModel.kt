package com.sentigo.bangkit.sentigoapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.RegisterResponse
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: AppRepository) : ViewModel() {

    val registerResponse : LiveData<Result<RegisterResponse>> get() = repo.registerResponse

    fun registerUser(username: String, email:String, password: String) {
        viewModelScope.launch{
            repo.registerUser(username, email, password)
        }
    }
}