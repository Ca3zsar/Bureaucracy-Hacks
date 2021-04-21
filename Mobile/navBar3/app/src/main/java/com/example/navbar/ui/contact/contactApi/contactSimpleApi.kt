package com.example.navbar.ui.contact.contactApi

import com.example.navbar.ui.contact.contactModel.contactPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface contactSimpleApi {
    //@GET("changepassword")
    @GET("posts/1")
    suspend fun getPost(): Response<contactPost>

    @POST("https://bureaucracyhackshostat.herokuapp.com/user/contact/")
    suspend fun pushPost(
            @Body post : contactPost
    ): Response<contactPost>
}