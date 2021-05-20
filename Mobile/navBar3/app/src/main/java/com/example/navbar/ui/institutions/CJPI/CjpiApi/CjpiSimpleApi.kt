package com.example.navbar.ui.institutions.CJPI.CjpiApi

import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiPost
import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiProcesses
import retrofit2.Response
import retrofit2.http.GET

interface CjpiSimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Casa Județeană de Pensii Iași")
    suspend fun getPost(): Response<CjpiPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/CJPI")
    suspend fun getProcessesListPost(): List<Response<CjpiProcesses>>
}