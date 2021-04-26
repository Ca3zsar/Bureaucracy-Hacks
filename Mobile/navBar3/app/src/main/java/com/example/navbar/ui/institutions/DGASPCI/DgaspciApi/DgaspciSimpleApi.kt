package com.example.navbar.ui.institutions.DGASPCI.DgaspciApi

import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciPost
import retrofit2.Response
import retrofit2.http.GET

interface DgaspciSimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<DgaspciPost>
}