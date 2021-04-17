package com.example.navbar.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.register.registerRepostory.rgstrRepostory
import com.example.navbar.ui.register.registerModel.registerPost
import kotlinx.coroutines.launch
import retrofit2.Response

class registerViewModel(private val repository: rgstrRepostory) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is register Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<registerPost>> = MutableLiveData()
    /**
    fun getPost() {
    viewModelScope.launch{
    val response = repository.getPost()
    myResponse.value = response
    }
    }
     */

    fun pushPost(post : registerPost) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }
}