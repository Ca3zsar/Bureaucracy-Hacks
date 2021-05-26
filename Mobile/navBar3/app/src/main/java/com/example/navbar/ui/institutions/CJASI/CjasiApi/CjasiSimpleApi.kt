package com.example.navbar.ui.institutions.CJASI.CjasiApi

import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiPost
import retrofit2.Response
import retrofit2.http.GET

interface CjasiSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Casa Judeteana de Asigurari de Sanatate Iasi")
    suspend fun getPost(): Response<CjasiPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/Casa Judeteana de Asigurari de Sanatate Iasi")
    suspend fun getProcessesListPost(): List<Map<String, String>>
}