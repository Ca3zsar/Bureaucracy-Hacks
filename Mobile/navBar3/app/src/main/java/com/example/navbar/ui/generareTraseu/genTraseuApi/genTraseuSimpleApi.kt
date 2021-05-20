package com.example.navbar.ui.generareTraseu.genTraseuApi

import com.example.navbar.ui.generareTraseu.genTraseuModel.proceseModel
import retrofit2.Response
import retrofit2.http.GET

interface genTraseuSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/processeslist")
    suspend fun getProcese(): Response<List<proceseModel>>
}