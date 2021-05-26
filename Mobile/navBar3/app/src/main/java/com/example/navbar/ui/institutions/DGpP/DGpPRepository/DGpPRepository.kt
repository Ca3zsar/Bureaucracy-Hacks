package com.example.navbar.ui.institutions.DGpP.DGpPRepository

import com.example.navbar.ui.institutions.DGpP.DGpPApi.DGpPRetrofitInstance
import com.example.navbar.ui.institutions.DGpP.DGpPModel.DGpPPost
import retrofit2.Response

class DGpPRepository {

    suspend fun getPost(): Response<DGpPPost> {
        return DGpPRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return DGpPRetrofitInstance.api2.getProcessesListPost()
    }
}