package com.example.navbar.ui.institutions.DGASPCI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciPost
import com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository.DgaspciRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DgaspciViewModel (private val repository: DgaspciRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<DgaspciPost>> = MutableLiveData()
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