package com.example.navbar.ui.institutions.SALUBRIS.SalubrisRepository

import com.example.navbar.ui.institutions.SALUBRIS.SalubrisApi.SalubrisRetrofitInstance
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisModel.SalubrisPost
import retrofit2.Response

class SalubrisRepository {
    suspend fun getPost(): Response<SalubrisPost> {
        return SalubrisRetrofitInstance.api.getPost()
    }
}