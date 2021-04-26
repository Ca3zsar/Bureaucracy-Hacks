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

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}