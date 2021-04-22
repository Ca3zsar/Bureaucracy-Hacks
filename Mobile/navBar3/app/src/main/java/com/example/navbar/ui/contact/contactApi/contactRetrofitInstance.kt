package com.example.navbar.ui.contact.contactApi

import com.example.navbar.ui.contact.contactUtil.contactConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object contactRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(contactConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : contactSimpleApi by lazy {
        retrofit.create(contactSimpleApi::class.java)
    }
}