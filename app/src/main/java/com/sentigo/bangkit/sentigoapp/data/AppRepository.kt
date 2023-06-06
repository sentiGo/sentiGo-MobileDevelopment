package com.sentigo.bangkit.sentigoapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sentigo.bangkit.sentigoapp.data.local.database.FavoriteDao
import com.sentigo.bangkit.sentigoapp.data.local.entity.FavoriteEntity
import com.sentigo.bangkit.sentigoapp.data.remote.api.ApiService
import com.sentigo.bangkit.sentigoapp.data.remote.response.*
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.model.UserModel
import com.sentigo.bangkit.sentigoapp.model.UserPreferences
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.HttpException

class AppRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences,
    private val db: FavoriteDao
) {

    private val _loginResponse = MutableLiveData<Result<LoginResult>?>(Result.Loading)
    val loginResponse: LiveData<Result<LoginResult>?> get() = _loginResponse

    private val _registerResponse = MutableLiveData<Result<RegisterResponse>>(Result.Loading)
    val registerResponse: LiveData<Result<RegisterResponse>> get() = _registerResponse

    private val _listHomeDestinasi = MutableLiveData<Result<List<ListDestinasiItem>>>(Result.Loading)
    val listHomeDestinasi: LiveData<Result<List<ListDestinasiItem>>> get() = _listHomeDestinasi

    private val _userResponse = MutableLiveData<Result<UserData>>(Result.Loading)
    val userResponse: LiveData<Result<UserData>> get() = _userResponse

    private val _detailDestinasiResponse = MutableLiveData<Result<DetailDestinasi>>(Result.Loading)
    val detailDestinasiResponse: LiveData<Result<DetailDestinasi>> get() = _detailDestinasiResponse

    private val _changePasswordUserResponse = MutableLiveData<Result<RegisterResponse>>(Result.Loading)
    val changePasswordUserResponse: LiveData<Result<RegisterResponse>> get() = _changePasswordUserResponse

    private val _updatePhotoResponse = MutableLiveData<Result<UpdatePhotoResponse>>(Result.Loading)
    val updatePhotoResponse: LiveData<Result<UpdatePhotoResponse>> get() = _updatePhotoResponse

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
            val response = apiService.getRatingDestinasi("Bearer $token")
            _listHomeDestinasi.value = Result.Success(response.listDestinasi)
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _listHomeDestinasi.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _listHomeDestinasi.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getListLocationDestinasi(token: String, lat: Double?, lon: Double?) {
        _listHomeDestinasi.value = Result.Loading
        try {
            val response = apiService.getLocationDestinasi("Bearer $token", lat, lon)
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
            if (id != 0) {
                val response = apiService.getUser("Bearer $token", id)
                _userResponse.value = Result.Success(response.userData)
            }
        } catch (e: HttpException) {
            Log.d("AppRepository", e.response().toString())
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _userResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _userResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getDestinasiDetail(token: String, id: Int) {
        _detailDestinasiResponse.value = Result.Loading
        try {
            val response = apiService.getDestinasiDetail("Bearer $token", id)
            _detailDestinasiResponse.value = Result.Success(response.detailDestinasi)
        }catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _detailDestinasiResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _detailDestinasiResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun putChangePassword(token: String, id: Int, oldPass: String?, newPass: String?) {
        _changePasswordUserResponse.value = Result.Loading
        try {
            val response = apiService.changePasswordUser("Bearer $token", id, oldPass, newPass)
            _changePasswordUserResponse.value = Result.Success(response)
        } catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _changePasswordUserResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _changePasswordUserResponse.value = Result.Error(e.message.toString())
        }
    }

    suspend fun updatePhotoProfile(token: String, photo: MultipartBody.Part, id: Int) {
        _updatePhotoResponse.value = Result.Loading
        try {
            val response = apiService.updatePhotoProfile("Bearer $token", photo, id)
            _updatePhotoResponse.value = Result.Success(response)
        } catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()?.let { JSONObject(it) }
            _updatePhotoResponse.postValue(error?.getString("message")?.let { Result.Error(it) })
        } catch (e: Exception) {
            _updatePhotoResponse.value = Result.Error(e.message.toString())
        }
    }

    fun getFavoriteDb(): LiveData<List<FavoriteEntity>> = db.getFavoriteDb()

    suspend fun saveFavoriteDb(data: FavoriteEntity) {
        db.saveFavoriteDb(data)
    }

    suspend fun deleteFavoriteDb(id: Int) {
        db.deleteFavoriteDb(id)
    }

    suspend fun saveUserPref(user: UserModel) {
        pref.saveUser(user)
    }

    suspend fun setLocationPref(lat: Double, lon: Double) {
        pref.setLocation(lat, lon)
    }

    suspend fun logout() {
        _loginResponse.value = null
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
            pref: UserPreferences,
            db: FavoriteDao
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService, pref, db)
            }.also { instance = it }
    }
}