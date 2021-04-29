package com.example.navbar.ui.generareTraseu.genTraseuApi

import com.example.navbar.ui.generareTraseu.genTraseuUtil.genTraseuConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object genTraseuRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: genTraseuSimpleApi by lazy {
        retrofit.create(genTraseuSimpleApi::class.java)
    }
}