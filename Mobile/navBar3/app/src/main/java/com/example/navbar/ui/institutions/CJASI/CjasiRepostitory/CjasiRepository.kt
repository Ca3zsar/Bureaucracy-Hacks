package com.example.navbar.ui.institutions.CJASI.CjasiRepostitory

import com.example.navbar.ui.institutions.CJASI.CjasiApi.CjasiRetrofitInstance
import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiPost
import com.example.navbar.ui.institutions.CJASI.CjasiModel.CjasiProcesses
import retrofit2.Response

class CjasiRepository {
    suspend fun getPost(): Response<CjasiPost> {
        return CjasiRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<CjasiProcesses>> {
        return CjasiRetrofitInstance.api.getProcessesListPost()
    }
}