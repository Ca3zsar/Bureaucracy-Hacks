package com.example.navbar.ui.institutions.DASI.DasiRepository

import com.example.navbar.ui.institutions.DASI.DasiApi.DasiRetrofitInstance
import com.example.navbar.ui.institutions.DASI.DasiModel.DasiPost
import retrofit2.Response

class DasiRepository {

    suspend fun getPost(): Response<DasiPost> {
        return DasiRetrofitInstance.api.getPost()
    }
}