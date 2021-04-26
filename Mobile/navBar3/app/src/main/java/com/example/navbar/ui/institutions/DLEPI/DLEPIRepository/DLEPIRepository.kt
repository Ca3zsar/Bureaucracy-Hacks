package com.example.navbar.ui.institutions.DLEPI.DLEPIRepository

import com.example.navbar.ui.institutions.DLEPI.DLEPIApi.DLEPIRetrofitInstance
import com.example.navbar.ui.institutions.DLEPI.DLEPIModel.DLEPIPost
import retrofit2.Response

class DLEPIRepository {

    suspend fun getPost(): Response<DLEPIPost> {
        return DLEPIRetrofitInstance.api.getPost()
    }
}