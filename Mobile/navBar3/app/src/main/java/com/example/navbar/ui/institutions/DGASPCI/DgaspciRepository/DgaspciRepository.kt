package com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository

import com.example.navbar.ui.institutions.DGASPCI.DgaspciApi.DgaspciRetrofitInstance
import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciPost
import com.example.navbar.ui.institutions.DGASPCI.DgaspciModel.DgaspciProcesses
import retrofit2.Response

class DgaspciRepository {

    suspend fun getPost(): Response<DgaspciPost> {
        return DgaspciRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<DgaspciProcesses>> {
        return DgaspciRetrofitInstance.api.getProcessesListPost()
    }
}