package com.example.navbar.ui.institutions.CJPI.CjpiApi

import com.example.navbar.ui.institutions.CJPI.CjpiUtil.CjpiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CjpiRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(CjpiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : CjpiSimpleApi by lazy {
        retrofit.create(CjpiSimpleApi::class.java)
    }
}