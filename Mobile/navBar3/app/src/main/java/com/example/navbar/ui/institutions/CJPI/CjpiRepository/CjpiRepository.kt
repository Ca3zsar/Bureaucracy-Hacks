package com.example.navbar.ui.institutions.CJPI.CjpiRepository

import com.example.navbar.ui.institutions.CJPI.CjpiApi.CjpiRetrofitInstance
import com.example.navbar.ui.institutions.CJPI.CjpiModel.CjpiPost
import retrofit2.Response

class CjpiRepository {
    suspend fun getPost(): Response<CjpiPost> {
        return CjpiRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return CjpiRetrofitInstance.api2.getProcessesListPost()
    }
}