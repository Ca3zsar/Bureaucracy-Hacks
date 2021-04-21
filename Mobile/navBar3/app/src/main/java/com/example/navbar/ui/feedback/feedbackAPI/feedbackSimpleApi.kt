package com.example.navbar.ui.feedback.feedbackAPI

import com.example.navbar.ui.feedback.feedbackModel.feedbackPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface feedbackSimpleApi {
    @GET("posts/1")
    suspend fun getPost(): Response<feedbackPost>

    @POST("https://bureaucracyhackshostat.herokuapp.com/user/Innoire buletin/feedbacks/")
    suspend fun pushPost(
        @Body post : feedbackPost
    ): Response<feedbackPost>
}