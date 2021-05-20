package com.example.navbar.ui.institutions.ANAF.AnafRepository

import com.example.navbar.ui.institutions.ANAF.AnafApi.AnafRetrofitInstance
import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafPost
import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafProcesses
import retrofit2.Response

class AnafRepository {

    suspend fun getPost(): Response<AnafPost> {
        return AnafRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Response<AnafProcesses>> {
        return AnafRetrofitInstance.api.getProcessesListPost()
    }
}