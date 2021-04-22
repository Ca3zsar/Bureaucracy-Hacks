package com.example.navbar.ui.generareTraseu.generareTraseuApi

import com.example.navbar.ui.generareTraseu.generareTraseuModel.departmentsListPost
import retrofit2.Response
import retrofit2.http.GET

interface departmentsListSimpleApi {
    @GET("posts/1")
    suspend fun getDepartmentsPost(): Response<departmentsListPost>
}