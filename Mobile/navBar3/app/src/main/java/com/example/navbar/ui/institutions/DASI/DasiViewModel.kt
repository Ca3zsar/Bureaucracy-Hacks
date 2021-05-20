package com.example.navbar.ui.institutions.DASI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.DASI.DasiModel.DasiPost
import com.example.navbar.ui.institutions.DASI.DasiModel.DasiProcesses
import com.example.navbar.ui.institutions.DASI.DasiRepository.DasiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DasiViewModel (private val repository: DasiRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<DasiPost>> = MutableLiveData()
    val myResponse2: MutableLiveData<List<Response<DasiProcesses>>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }

    fun getProcessesListPost() {
        viewModelScope.launch {
            val response = repository.getProcessesListPost()
            myResponse2.value = response
        }
    }
}