package com.sentigo.bangkit.sentigoapp.ui.home

import androidx.lifecycle.ViewModel
import com.sentigo.bangkit.sentigoapp.data.AppRepository

class HomeViewModel(private val repo: AppRepository) : ViewModel() {

    val getUser = repo.getUserPref()
}