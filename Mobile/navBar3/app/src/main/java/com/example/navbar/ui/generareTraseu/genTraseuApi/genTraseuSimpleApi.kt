package com.example.navbar.ui.generareTraseu.genTraseuApi

import com.example.navbar.ui.generareTraseu.genTraseuModel.genTraseuPost
import retrofit2.Response
import retrofit2.http.GET

interface genTraseuSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/institutionslist")
    suspend fun getPost(): Response<List<genTraseuPost>>
}