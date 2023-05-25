package com.sentigo.bangkit.sentigoapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sentigo.bangkit.sentigoapp.data.remote.api.ApiService
import com.sentigo.bangkit.sentigoapp.data.remote.response.LoginResult
import com.sentigo.bangkit.sentigoapp.data.remote.response.RegisterResponse
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.model.UserModel
import com.sentigo.bangkit.sentigoapp.model.UserPreferences
import org.json.JSONObject
import retrofit2.HttpException

class AppRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _loginResponse = MutableLiveData<Result<LoginResult>>(Result.Loading)
    val loginResponse: LiveData<Result<LoginResult>> get() = _loginResponse

    private val _registerResponse = MutableLiveData<Result<RegisterResponse>>(Result.Loading)
    val registerResponse: LiveData<Result<RegisterResponse>> get() = _registerResponse


    suspend fun loginUser(email: String, password: String) {
        _loginResponse.value = Result.Loading
        try {
            val response = apiService.loginUser(email, password)
            _loginResponse.value = Result.Success(response.loginResult)
        } catch (e: HttpException) {
            Log.d("AppRepository", "loginUser: ${e.message()}")
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _loginResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        }
    }

    suspend fun saveUserPref(user: UserModel) {
        pref.saveUser(user)
    }

    suspend fun setLocationPref(lat: Float, lon: Float) {
        pref.setLocation(lat, lon)
    }

    suspend fun logout() {
//        _loginResponse.value = null
        pref.logout()
    }

    fun getUserPref(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun registerUser(username: String, email: String, password: String) {
        _registerResponse.value = Result.Loading
        try {
            val response = apiService.registerUser(username, email, password)
            _registerResponse.value = Result.Success(response)
        } catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _registerResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService, pref)
            }.also { instance = it }
    }
}