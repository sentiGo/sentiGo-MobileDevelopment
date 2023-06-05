package com.sentigo.bangkit.sentigoapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.ui.detail.DetailViewModel
import com.sentigo.bangkit.sentigoapp.ui.favorite.FavoriteViewModel
import com.sentigo.bangkit.sentigoapp.ui.find.FindViewModel
import com.sentigo.bangkit.sentigoapp.ui.home.HomeViewModel
import com.sentigo.bangkit.sentigoapp.ui.login.LoginViewModel
import com.sentigo.bangkit.sentigoapp.ui.profile.ProfileViewModel
import com.sentigo.bangkit.sentigoapp.ui.profile.sheet.ChangePasswordSheetViewModel
import com.sentigo.bangkit.sentigoapp.ui.profile.sheet.ProfilePhotoViewModel
import com.sentigo.bangkit.sentigoapp.ui.register.RegisterViewModel

class ViewModelFactory(private val pref: AppRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(pref) as T
            }
            modelClass.isAssignableFrom(FindViewModel::class.java) -> {
                FindViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ChangePasswordSheetViewModel::class.java) -> {
                ChangePasswordSheetViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfilePhotoViewModel::class.java) -> {
                ProfilePhotoViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.providerRepository(context))
            }.also { instance = it }
    }
}