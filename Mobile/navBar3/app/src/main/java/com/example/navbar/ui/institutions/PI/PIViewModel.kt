package com.example.navbar.ui.institutions.PI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import com.example.navbar.ui.institutions.PI.PIRepository.PIRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PIViewModel (private val repository: PIRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<PIPost>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}