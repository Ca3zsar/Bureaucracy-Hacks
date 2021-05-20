package com.example.navbar.ui.institutions.DGpP.DGpPRepository

import com.example.navbar.ui.institutions.DGpP.DGpPApi.DGpPRetrofitInstance
import com.example.navbar.ui.institutions.DGpP.DGpPModel.DGpPPost
import com.example.navbar.ui.institutions.DGpP.DGpPModel.DgppProcesses
import retrofit2.Response

class DGpPRepository {

    suspend fun getPost(): Response<DGpPPost> {
        return DGpPRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<DgppProcesses>> {
        return DGpPRetrofitInstance.api.getProcessesListPost()
    }
}