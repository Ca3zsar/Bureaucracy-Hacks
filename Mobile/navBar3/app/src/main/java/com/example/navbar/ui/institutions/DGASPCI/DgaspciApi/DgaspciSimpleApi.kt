package com.example.navbar.ui.institutions.DGASPCI.DgaspciApi

import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciPost
import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciProcesses
import retrofit2.Response
import retrofit2.http.GET

interface DgaspciSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Directia Generala de Asistenta Sociala si Protectia Copilului Iasi")
    suspend fun getPost(): Response<DgaspciPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/DGASPCI")
    suspend fun getProcessesListPost(): List<Response<DgaspciProcesses>>
}