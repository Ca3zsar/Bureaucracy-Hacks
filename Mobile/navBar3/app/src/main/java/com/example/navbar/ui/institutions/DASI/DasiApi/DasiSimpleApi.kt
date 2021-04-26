package com.example.navbar.ui.institutions.DASI.DasiApi

import com.example.navbar.ui.institutions.DASI.DasiModel.DasiPost
import retrofit2.Response
import retrofit2.http.GET

interface DasiSimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<DasiPost>
}