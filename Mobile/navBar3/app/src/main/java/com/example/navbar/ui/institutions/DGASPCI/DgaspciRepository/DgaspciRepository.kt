package com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository

import com.example.navbar.ui.institutions.DGASPCI.DgaspciApi.DgaspciRetrofitInstance
import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciPost
import retrofit2.Response

class DgaspciRepository {

    suspend fun getPost(): Response<DgaspciPost> {
        return DgaspciRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return DgaspciRetrofitInstance.api2.getProcessesListPost()
    }
}