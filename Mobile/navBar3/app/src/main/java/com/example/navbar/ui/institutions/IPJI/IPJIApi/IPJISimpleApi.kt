package com.example.navbar.ui.institutions.IPJI.IPJIApi

import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IpjiProcesses
import retrofit2.Response
import retrofit2.http.GET

interface IPJISimpleApi {

    @GET("https://bureaucracyhackshostat.herokuapp.com/user/institution/Inspectoratul de Politie Judetean Iasi")
    suspend fun getPost(): Response<IPJIPost>

    @GET ("https://bureaucracyhackshostat.herokuapp.com/processeslist/IPJI")
    suspend fun getProcessesListPost(): List<Response<IpjiProcesses>>
}