package com.example.navbar.ui.institutions.SALUBRIS.SalubrisApi

import com.example.navbar.ui.institutions.SALUBRIS.SalubrisModel.SalubrisPost
import retrofit2.Response
import retrofit2.http.GET

interface SalubrisSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/SalubrIS")
    suspend fun getPost(): Response<SalubrisPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/SalubrIS")
    suspend fun getProcessesListPost(): List<Map<String, String>>
}