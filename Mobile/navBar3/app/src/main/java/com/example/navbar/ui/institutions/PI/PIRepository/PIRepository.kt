package com.example.navbar.ui.institutions.PI.PIRepository

import com.example.navbar.ui.institutions.IPJI.IPJIApi.IPJIRetrofitInstance
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import com.example.navbar.ui.institutions.PI.PIApi.PIRetrofitInstance
import com.example.navbar.ui.institutions.PI.PIModel.PIPost
import com.example.navbar.ui.institutions.PI.PIModel.PIProcesses
import retrofit2.Response

class PIRepository {

    suspend fun getPost(): Response<PIPost> {
        return PIRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<PIProcesses>> {
        return PIRetrofitInstance.api.getProcessesListPost()
    }
}