package com.sentigo.bangkit.sentigoapp.ui.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.di.Result
import kotlinx.coroutines.launch

class FindViewModel(private val repo: AppRepository) : ViewModel() {

    val getUserPref = repo.getUserPref()
    val listFindResponse: LiveData<Result<List<ListDestinasiItem>>> = repo.listFindResponse

    fun getListFind(token: String, desc: String) {
        viewModelScope.launch {
            repo.getListFind(token, desc)
        }
    }
}