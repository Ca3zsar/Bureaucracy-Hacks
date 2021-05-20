package com.example.navbar.ui.institutions.ANAF.AnafApi

import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafPost
import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafProcesses
import retrofit2.Response
import retrofit2.http.GET

interface AnafSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/ANAF")
    suspend fun getPost(): Response<AnafPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/ANAF")
    suspend fun getProcessesListPost(): List<Response<AnafProcesses>>
}