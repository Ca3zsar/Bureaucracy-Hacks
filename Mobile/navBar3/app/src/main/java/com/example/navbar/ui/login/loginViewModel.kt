package com.example.navbar.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.changePassword.changePasswordRepository.changePasswordRepository
import com.example.navbar.ui.login.loginModel.lgnReques
import com.example.navbar.ui.login.loginModel.loginPost
import com.example.navbar.ui.login.loginRepository.lgnRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class loginViewModel(private val repository: lgnRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<lgnReques>> = MutableLiveData()
    /**
    fun getPost() {
    viewModelScope.launch{
    val response = repository.getPost()
    myResponse.value = response
    }
    }
     */

    fun pushPost(post : loginPost) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }
}