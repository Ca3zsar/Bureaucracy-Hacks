package com.example.navbar.ui.generareTraseu.genTraseuRepository

import com.example.navbar.ui.generareTraseu.genTraseuApi.genTraseuRetrofitInstance
import com.example.navbar.ui.generareTraseu.genTraseuModel.proceseModel
import retrofit2.Response

class genTraseuRepository {

    suspend fun getProcese(): Response<List<proceseModel>> {
        return genTraseuRetrofitInstance.api.getProcese()
    }
}