package com.example.navbar.ui.generareTraseu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TraseuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is traseu Fragment"
    }
    val text: LiveData<String> = _text
}