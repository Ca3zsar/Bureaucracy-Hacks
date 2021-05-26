package com.example.navbar.ui.institutions.DLEPI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DLEPIPost
import com.example.navbar.ui.institutions.DLEPI.DLEPIRepository.DLEPIRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DLEPIViewModel (private val repository: DLEPIRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<DLEPIPost>> = MutableLiveData()
    val myResponse2: MutableLiveData<List<Map<String, String>>> = MutableLiveData()

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