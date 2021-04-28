package com.example.navbar.ui.generareMap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomtom.online.sdk.common.location.LatLng

class MapFragmentTraseuViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is map fragment"
    }
    val text: LiveData<String> = _text
}