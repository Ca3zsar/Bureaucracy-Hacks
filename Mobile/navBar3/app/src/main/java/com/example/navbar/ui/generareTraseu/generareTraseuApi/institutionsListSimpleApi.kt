package com.example.navbar.ui.generareTraseu.generareTraseuApi

import com.example.navbar.ui.generareTraseu.generareTraseuModel.institutionsListPost
import retrofit2.Response
import retrofit2.http.GET

interface institutionsListSimpleApi {

    /**/
    @GET("posts/1")
    suspend fun getInstitutionsPost(): Response<institutionsListPost>
}