package com.example.navbar.ui.institutions.DASI.DasiRepository

import com.example.navbar.ui.institutions.DASI.DasiApi.DasiRetrofitInstance
import com.example.navbar.ui.institutions.DASI.DasiModel.DasiPost
import com.example.navbar.ui.institutions.DASI.DasiModel.DasiProcesses
import retrofit2.Response

class DasiRepository {

    suspend fun getPost(): Response<DasiPost> {
        return DasiRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<DasiProcesses>> {
        return DasiRetrofitInstance.api.getProcessesListPost()
    }
}