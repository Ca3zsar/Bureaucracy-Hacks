package com.example.navbar.ui.institutions.IPJI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import com.example.navbar.ui.institutions.IPJI.IPJIRepository.IPJIRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class IPJIViewModel (private val repository: IPJIRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<IPJIPost>> = MutableLiveData()
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