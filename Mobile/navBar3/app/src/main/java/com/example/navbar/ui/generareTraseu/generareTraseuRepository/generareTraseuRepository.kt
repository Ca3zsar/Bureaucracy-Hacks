package com.example.navbar.ui.generareTraseu.generareTraseuRepository

import com.example.navbar.ui.generareTraseu.generareTraseuApi.generareTraseuRetrofitInstance
import com.example.navbar.ui.generareTraseu.generareTraseuModel.departmentsListPost
import com.example.navbar.ui.generareTraseu.generareTraseuModel.institutionsListPost
import com.example.navbar.ui.generareTraseu.generareTraseuModel.processesListPost
import retrofit2.Response

class generareTraseuRepository {

    suspend fun getDepartmentsPost(): Response<departmentsListPost> {
        return generareTraseuRetrofitInstance.departmentsApi.getDepartmentsPost()
    }

    suspend fun getInstitutionsPost(): Response<institutionsListPost> {
        return generareTraseuRetrofitInstance.institutionsApi.getInstitutionsPost()
    }

    suspend fun getProcessesPost(): Response<processesListPost> {
        return generareTraseuRetrofitInstance.processesApi.getProcessesPost()
    }
}