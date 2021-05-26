package com.example.navbar.ui.institutions.ANAF.AnafRepository

import com.example.navbar.ui.institutions.ANAF.AnafApi.AnafRetrofitInstance
import com.example.navbar.ui.institutions.ANAF.AnafModel.AnafPost
import retrofit2.Response

class AnafRepository {

    suspend fun getPost(): Response<AnafPost> {
        return AnafRetrofitInstance.api.getPost()
    }

    suspend fun getProcessesListPost(): List<Map<String, String>> {
        return AnafRetrofitInstance.api2.getProcessesListPost()
    }
}