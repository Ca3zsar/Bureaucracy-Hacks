package com.example.navbar.ui.institutions.PI.PIApi

import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import retrofit2.Response
import retrofit2.http.GET

interface PISimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<PIPost>
}