package com.example.navbar.ui.generareMap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.generareMap.mapModel.mapResponse
import com.example.navbar.ui.generareMap.mapModel.mapRequest
import com.example.navbar.ui.generareMap.mapRepository.mpRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MapFragmentTraseuViewModel(private val mapRepository: mpRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is map Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<mapResponse>> = MutableLiveData()

    fun pushPost(post : mapRequest) {
        viewModelScope.launch {
            val response = mapRepository.pushPost(post)
            myResponse.value = response;
        }
    }
}