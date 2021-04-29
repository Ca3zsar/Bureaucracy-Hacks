package com.example.navbar.ui.generareTraseu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.generareTraseu.genTraseuModel.genTraseuPost
import com.example.navbar.ui.generareTraseu.genTraseuRepository.genTraseuRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class genTraseuViewModel(private val genTrRepository: genTraseuRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is feedback Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<List<genTraseuPost>>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = genTrRepository.getPost()
            myResponse.value = response
        }
    }
}