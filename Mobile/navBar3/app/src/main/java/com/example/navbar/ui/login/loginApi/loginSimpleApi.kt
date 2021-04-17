package com.example.navbar.ui.login.loginApi

import com.example.navbar.ui.login.loginModel.loginPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface loginSimpleApi {

    //@GET("changepassword")
    @GET("posts/1")
    suspend fun getPost(): Response<loginPost>

    @POST("https://bureaucracyhackshostat.herokuapp.com/login")
    suspend fun pushPost(
            @Body post : loginPost
    ): Response<loginPost>
}