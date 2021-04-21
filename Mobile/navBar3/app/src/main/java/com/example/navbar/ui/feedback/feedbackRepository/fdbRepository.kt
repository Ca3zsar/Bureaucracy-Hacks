package com.example.navbar.ui.feedback.feedbackRepository

import com.example.navbar.ui.feedback.feedbackAPI.feedbackRetrofitInstance
import com.example.navbar.ui.feedback.feedbackModel.feedbackPost
import retrofit2.Response

class fdbRepository {
    suspend fun pushPost(post : feedbackPost): Response<feedbackPost> {
        return feedbackRetrofitInstance.api.pushPost(post)
    }
}