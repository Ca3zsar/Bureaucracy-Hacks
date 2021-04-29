package com.example.navbar.ui.generareTraseu.genTraseuRepository

import com.example.navbar.ui.generareTraseu.genTraseuApi.genTraseuRetrofitInstance
import com.example.navbar.ui.generareTraseu.genTraseuModel.genTraseuPost
import retrofit2.Response

class genTraseuRepository {

    suspend fun getPost(): Response<List<genTraseuPost>> {
        return genTraseuRetrofitInstance.api.getPost()
    }
}