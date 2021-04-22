package com.example.navbar.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.contact.contactModel.contactPost
import com.example.navbar.ui.contact.contactRepository.contactRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class contactViewModel(private val repository: contactRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<contactPost>> = MutableLiveData()

    fun pushPost(post : contactPost) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }
}