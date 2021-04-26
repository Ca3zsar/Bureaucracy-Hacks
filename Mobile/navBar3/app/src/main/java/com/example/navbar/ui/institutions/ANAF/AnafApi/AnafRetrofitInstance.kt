package com.example.navbar.ui.institutions.ANAF.AnafApi

import com.example.navbar.ui.institutions.ANAF.AnafUtil.AnafConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnafRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : AnafSimpleApi by lazy {
        retrofit.create(AnafSimpleApi::class.java)
    }
}