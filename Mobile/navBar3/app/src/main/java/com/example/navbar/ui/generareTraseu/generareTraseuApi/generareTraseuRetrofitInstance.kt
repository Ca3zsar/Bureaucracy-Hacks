package com.example.navbar.ui.generareTraseu.generareTraseuApi

import com.example.navbar.ui.generareTraseu.generareTraseuUtil.departmentsListConstants
import com.example.navbar.ui.generareTraseu.generareTraseuUtil.institutionsListConstants
import com.example.navbar.ui.generareTraseu.generareTraseuUtil.processesListConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object generareTraseuRetrofitInstance {

    private val departmentsRetrofit by lazy {
        Retrofit.Builder()
                .baseUrl(departmentsListConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val departmentsApi : departmentsListSimpleApi by lazy {
        departmentsRetrofit.create(departmentsListSimpleApi::class.java)
    }

    private val institutionsRetrofit by lazy {
        Retrofit.Builder()
                .baseUrl(institutionsListConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val institutionsApi : institutionsListSimpleApi by lazy {
        institutionsRetrofit.create(institutionsListSimpleApi::class.java)
    }

    private val processesRetrofit by lazy {
        Retrofit.Builder()
                .baseUrl(processesListConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val processesApi : processesListSimpleApi by lazy {
        processesRetrofit.create(processesListSimpleApi::class.java)
    }
}