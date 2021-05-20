package com.example.navbar.ui.institutions.SALUBRIS.SalubrisApi

import com.example.navbar.ui.institutions.SALUBRIS.SalubrisUtil.SalubrisConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SalubrisRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SalubrisConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : SalubrisSimpleApi by lazy {
        retrofit.create(SalubrisSimpleApi::class.java)
    }
}