package com.example.navbar.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class searchViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Search"
    }
    val text: LiveData<String> = _text
}