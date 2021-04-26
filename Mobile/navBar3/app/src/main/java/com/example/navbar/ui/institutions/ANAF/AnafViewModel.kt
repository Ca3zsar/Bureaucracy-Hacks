package com.example.navbar.ui.institutions.ANAF

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafPost
import com.example.navbar.ui.institutions.ANAF.AnafRepository.AnafRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AnafViewModel(private val repository: AnafRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<AnafPost>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}