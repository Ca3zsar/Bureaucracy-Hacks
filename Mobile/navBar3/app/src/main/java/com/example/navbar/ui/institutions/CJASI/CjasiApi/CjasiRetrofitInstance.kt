package com.example.navbar.ui.institutions.CJASI.CjasiApi

import com.example.navbar.ui.institutions.CJASI.CjasiUtil.CjasiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CjasiRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(CjasiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : CjasiSimpleApi by lazy {
        retrofit.create(CjasiSimpleApi::class.java)
    }
}