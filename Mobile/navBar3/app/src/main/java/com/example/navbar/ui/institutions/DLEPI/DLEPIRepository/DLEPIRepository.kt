package com.example.navbar.ui.institutions.DLEPI.DLEPIRepository

import com.example.navbar.ui.institutions.DLEPI.DLEPIApi.DLEPIRetrofitInstance
import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DLEPIPost
import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DlepiProcesses
import retrofit2.Response

class DLEPIRepository {

    suspend fun getPost(): Response<DLEPIPost> {
        return DLEPIRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<DlepiProcesses>> {
        return DLEPIRetrofitInstance.api.getProcessesListPost()
    }
}