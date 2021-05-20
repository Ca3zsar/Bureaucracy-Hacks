package com.example.navbar.ui.institutions.PI.PIApi

import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import com.example.navbar.ui.institutions.PI.PIModel.PIProcesses
import retrofit2.Response
import retrofit2.http.GET

interface PISimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Primaria Iasi")
    suspend fun getPost(): Response<PIPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/PI")
    suspend fun getProcessesListPost(): List<Response<PIProcesses>>
}