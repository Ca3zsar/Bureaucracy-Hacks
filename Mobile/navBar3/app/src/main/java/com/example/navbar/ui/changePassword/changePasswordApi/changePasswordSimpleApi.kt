package com.example.navbar.ui.changePassword.changePasswordApi

import com.example.navbar.ui.changePassword.changePasswordModel.changePasswordPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface changePasswordSimpleApi {

    //@GET("changepassword")
    @GET("posts/1")
    suspend fun getPost(): Response<changePasswordPost>

    @POST("https://bureaucracyhackshostat.herokuapp.com/changepassword/")
    suspend fun pushPost(
        @Body post : changePasswordPost
    ): Response<changePasswordPost>
}