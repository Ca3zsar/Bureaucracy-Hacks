package com.example.navbar.ui.institutions.PI.PIApi

import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import retrofit2.Response
import retrofit2.http.GET

interface PISimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Primaria Iasi")
    suspend fun getPost(): Response<PIPost>
}