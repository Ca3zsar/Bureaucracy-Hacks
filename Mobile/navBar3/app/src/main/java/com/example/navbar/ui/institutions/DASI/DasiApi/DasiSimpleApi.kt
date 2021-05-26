package com.example.navbar.ui.institutions.DASI.DasiApi

import com.example.navbar.ui.institutions.DASI.DasiModel.DasiPost
import retrofit2.Response
import retrofit2.http.GET

interface DasiSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Directia de Asistenta Sociala Iasi")
    suspend fun getPost(): Response<DasiPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/Directia de Asistenta Sociala Iasi")
    suspend fun getProcessesListPost(): List<Map<String, String>>
}