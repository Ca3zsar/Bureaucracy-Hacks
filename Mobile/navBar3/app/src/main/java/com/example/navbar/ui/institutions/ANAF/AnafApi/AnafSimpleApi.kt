package com.example.navbar.ui.institutions.ANAF.AnafApi

import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafPost
import retrofit2.Response
import retrofit2.http.GET

interface AnafSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/ANAF")
    suspend fun getPost(): Response<AnafPost>
}