package com.sentigo.bangkit.sentigoapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sentigo.bangkit.sentigoapp.data.remote.api.ApiService
import com.sentigo.bangkit.sentigoapp.data.remote.response.*
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

    private val _listHomeDestinasi = MutableLiveData<Result<List<ListDestinasiItem>>>(Result.Loading)
    val listHomeDestinasi: LiveData<Result<List<ListDestinasiItem>>> get() = _listHomeDestinasi

    private val _userResponse = MutableLiveData<Result<UserData>>(Result.Loading)
    val userResponse: LiveData<Result<UserData>> get() = _userResponse

    private val _detailDestinasiResponse = MutableLiveData<Result<DetailDestinasi>>(Result.Loading)
    val detailDestinasiResponse: LiveData<Result<DetailDestinasi>> get() = _detailDestinasiResponse

    suspend fun loginUser(email: String, password: String) {
        _loginResponse.value = Result.Loading
        try {
            val response = apiService.loginUser(email, password)
            _loginResponse.value = Result.Success(response.loginResult)
        } catch (e: HttpException) {
            Log.d("AppRepository", "loginUser: ${e.message()}")
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _loginResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _loginResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun registerUser(username: String, email: String, password: String) {
        _registerResponse.value = Result.Loading
        try {
            val response = apiService.registerUser(username, email, password)
            _registerResponse.value = Result.Success(response)
        } catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _registerResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _registerResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getListRatingDestinasi(token: String) {
        _listHomeDestinasi.value = Result.Loading
        try {
            val response = apiService.getRatingDestinasi(token)
            _listHomeDestinasi.value = Result.Success(response.listDestinasi)
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _listHomeDestinasi.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _listHomeDestinasi.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getUser(token: String, id: Int) {
        _userResponse.value = Result.Loading
        try {
            val response = apiService.getUser(token, id)
            _userResponse.value = Result.Success(response.userData)
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _userResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _userResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getDestinasiDetail(token: String, id: Int) {
        _detailDestinasiResponse.value = Result.Loading
        try {
            val response = apiService.getDestinasiDetail(token, id)
            _detailDestinasiResponse.value = Result.Success(response.detailDestinasi)
        }catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _detailDestinasiResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _detailDestinasiResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun saveUserPref(user: UserModel) {
        pref.saveUser(user)
    }

    suspend fun setLocationPref(lat: Float, lon: Float) {
        pref.setLocation(lat, lon)
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getUserPref(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
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