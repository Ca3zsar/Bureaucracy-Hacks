package com.example.navbar.ui.institutions.CJPI.CjpiApi

import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiPost
import retrofit2.Response
import retrofit2.http.GET

interface CjpiSimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<CjpiPost>
}