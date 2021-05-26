package com.example.navbar.ui.institutions.PI.PIRepository

import com.example.navbar.ui.institutions.PI.PIApi.PIRetrofitInstance
import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import retrofit2.Response

class PIRepository {

    suspend fun getPost(): Response<PIPost> {
        return PIRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return PIRetrofitInstance.api2.getProcessesListPost()
    }
}