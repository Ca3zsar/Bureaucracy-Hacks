package com.example.navbar.ui.institutions.DGpP.DGpPApi

import com.example.navbar.ui.institutions.DGpP.DGpPModel.DGpPPost
import com.example.navbar.ui.institutions.DGpP.DGpPModel.DgppProcesses
import retrofit2.Response
import retrofit2.http.GET

interface DGpPSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Direcția Generală de Pașapoarte")
    suspend fun getPost(): Response<DGpPPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/DGpP")
    suspend fun getProcessesListPost(): List<Response<DgppProcesses>>
}