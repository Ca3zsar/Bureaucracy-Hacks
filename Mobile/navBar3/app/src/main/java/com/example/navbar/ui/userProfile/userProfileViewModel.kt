package com.example.navbar.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class userProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home"
    }
    val text: LiveData<String> = _text
}