package com.example.navbar.ui.institutions.DGpP

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.DGpP.DGpPModel.DGpPPost
import com.example.navbar.ui.institutions.DGpP.DGpPRepository.DGpPRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DGpPViewModel (private val repository: DGpPRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<DGpPPost>> = MutableLiveData()
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