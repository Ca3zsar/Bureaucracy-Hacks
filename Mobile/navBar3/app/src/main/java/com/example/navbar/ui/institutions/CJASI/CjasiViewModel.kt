package com.example.navbar.ui.institutions.CJASI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiPost
import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiProcesses
import com.example.navbar.ui.institutions.CJASI.CjasiRepostitory.CjasiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CjasiViewModel(private val repository: CjasiRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<CjasiPost>> = MutableLiveData()
    val myResponse2: MutableLiveData<List<Response<CjasiProcesses>>> = MutableLiveData()

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