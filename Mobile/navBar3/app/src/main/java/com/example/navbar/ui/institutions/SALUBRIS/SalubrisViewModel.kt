package com.example.navbar.ui.institutions.SALUBRIS

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisModel.SalubrisPost
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisRepository.SalubrisRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SalubrisViewModel(private val repository: SalubrisRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<SalubrisPost>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}