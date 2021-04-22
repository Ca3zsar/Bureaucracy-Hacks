package com.example.navbar.ui.generareTraseu.generareTraseuApi

import com.example.navbar.ui.generareTraseu.generareTraseuModel.processesListPost
import retrofit2.Response
import retrofit2.http.GET

interface processesListSimpleApi {
    @GET("posts/1")
    suspend fun getProcessesPost(): Response<processesListPost>
}