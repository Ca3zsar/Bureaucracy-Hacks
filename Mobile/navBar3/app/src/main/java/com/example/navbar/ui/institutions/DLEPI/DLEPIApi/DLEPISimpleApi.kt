package com.example.navbar.ui.institutions.DLEPI.DLEPIApi

import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DLEPIPost
import retrofit2.Response
import retrofit2.http.GET

interface DLEPISimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Directia Locala pentru Evidenta Persoanelor Iasi")
    suspend fun getPost(): Response<DLEPIPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/Directia Locala pentru Evidenta Persoanelor Iasi")
    suspend fun getProcessesListPost(): List<Map<String, String>>
}