package com.example.navbar.ui.institutions.CJASI.CjasiApi

import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiPost
import retrofit2.Response
import retrofit2.http.GET

interface CjasiSimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<CjasiPost>
}