package com.example.navbar.ui.institutions.CJPI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiPost
import com.example.navbar.ui.institutions.CJPI.CjpiRepository.CjpiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CjpiViewModel(private val repository: CjpiRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<CjpiPost>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}