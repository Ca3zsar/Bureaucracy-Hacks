package com.example.navbar.ui.institutions.IPJI.IPJIRepository

import com.example.navbar.ui.institutions.IPJI.IPJIApi.IPJIRetrofitInstance
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IPJIPost
import com.example.navbar.ui.institutions.IPJI.IPJIModel.IpjiProcesses
import retrofit2.Response

class IPJIRepository {

    suspend fun getPost(): Response<IPJIPost> {
        return IPJIRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<IpjiProcesses>> {
        return IPJIRetrofitInstance.api.getProcessesListPost()
    }
}