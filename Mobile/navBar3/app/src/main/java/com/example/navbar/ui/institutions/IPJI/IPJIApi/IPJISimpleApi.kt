package com.example.navbar.ui.institutions.IPJI.IPJIApi

import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import retrofit2.Response
import retrofit2.http.GET

interface IPJISimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<IPJIPost>
}