package com.example.navbar.ui.generareTraseu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.generareTraseu.generareTraseuModel.departmentsListPost
import com.example.navbar.ui.generareTraseu.generareTraseuModel.institutionsListPost
import com.example.navbar.ui.generareTraseu.generareTraseuModel.processesListPost
import com.example.navbar.ui.generareTraseu.generareTraseuRepository.generareTraseuRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class TraseuViewModel(private val generareTraseuRepository: generareTraseuRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is traseu Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse1: MutableLiveData<Response<departmentsListPost>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<institutionsListPost>> = MutableLiveData()
    val myResponse3: MutableLiveData<Response<processesListPost>> = MutableLiveData()

    fun getDepartmentsPost(){
        viewModelScope.launch {
            val departmentsResponse: Response<departmentsListPost> = generareTraseuRepository.getDepartmentsPost()
            myResponse1.value = departmentsResponse
        }
    }
    fun getInstitutionsPost(){
        viewModelScope.launch {
            val institutionsResponse: Response<institutionsListPost> = generareTraseuRepository.getInstitutionsPost()
            myResponse2.value = institutionsResponse
        }
    }
    fun getProcessesPost(){
        viewModelScope.launch {
            val processesResponse: Response<processesListPost> = generareTraseuRepository.getProcessesPost()
            myResponse3.value = processesResponse
        }
    }
}