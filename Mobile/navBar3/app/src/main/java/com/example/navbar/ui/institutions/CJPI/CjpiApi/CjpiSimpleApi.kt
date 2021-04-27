package com.example.navbar.ui.institutions.CJPI.CjpiApi

import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiPost
import retrofit2.Response
import retrofit2.http.GET

interface CjpiSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Casa Judeteana de Pensii Iasi")
    suspend fun getPost(): Response<CjpiPost>
}