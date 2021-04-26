package com.example.navbar.ui.institutions.DLEPI.DLEPIApi

import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DLEPIPost
import retrofit2.Response
import retrofit2.http.GET

interface DLEPISimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<DLEPIPost>
}