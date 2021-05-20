package com.example.navbar.ui.institutions.SALUBRIS.SalubrisRepository

import com.example.navbar.ui.institutions.SALUBRIS.SalubrisApi.SalubrisRetrofitInstance
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisModel.SalubrisPost
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisModel.SalubrisProcesses
import retrofit2.Response

class SalubrisRepository {

    suspend fun getPost(): Response<SalubrisPost> {
        return SalubrisRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<SalubrisProcesses>> {
        return SalubrisRetrofitInstance.api.getProcessesListPost()
    }
}