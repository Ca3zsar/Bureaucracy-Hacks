package com.example.navbar.ui.register.registerApi

import com.example.navbar.ui.register.registerModel.registerPost
import com.example.navbar.ui.register.registerUtil.registerConstants
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class registerRetrofitInstance {

    //suspend fun getRegister() :Response<Albums>
    companion object {
        val BASE_URL = "https://bureaucracyhackshostat.herokuapp.com/"
        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(registerConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: registerSimpleApi by lazy {
            retrofit.create(registerSimpleApi::class.java)
        }
    }
}