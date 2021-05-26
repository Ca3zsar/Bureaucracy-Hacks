package com.example.navbar.ui.institutions.IPJI.IPJIRepository

import com.example.navbar.ui.institutions.IPJI.IPJIApi.IPJIRetrofitInstance
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import retrofit2.Response

class IPJIRepository {

    suspend fun getPost(): Response<IPJIPost> {
        return IPJIRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return IPJIRetrofitInstance.api2.getProcessesListPost()
    }
}