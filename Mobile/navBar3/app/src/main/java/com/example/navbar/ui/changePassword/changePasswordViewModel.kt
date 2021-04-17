package com.example.navbar.ui.changePassword


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.changePassword.changePasswordModel.changePasswordPost
import com.example.navbar.ui.changePassword.changePasswordRepository.changePasswordRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class changePasswordViewModel(private val repository: changePasswordRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is changePassword Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<changePasswordPost>> = MutableLiveData()
    /**
    fun getPost() {
        viewModelScope.launch{
            val response = repository.getPost()
            myResponse.value = response
        }
    }
    */

    fun pushPost(post : changePasswordPost) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }
}