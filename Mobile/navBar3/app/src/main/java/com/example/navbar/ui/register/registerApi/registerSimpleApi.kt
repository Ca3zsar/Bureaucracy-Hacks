package com.example.navbar.ui.register.registerApi


import com.example.navbar.ui.register.registerModel.registerPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface registerSimpleApi {
    //@GET("changepassword")

    @POST("registration")
    suspend fun pushPost(
        @Body post : registerPost
    ): Response<registerPost>
}