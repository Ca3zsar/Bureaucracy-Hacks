package com.example.navbar.ui.changePassword.changePasswordRepository

import com.example.navbar.ui.changePassword.changePasswordApi.changePasswordRetrofitInstance
import com.example.navbar.ui.changePassword.changePasswordModel.changePasswordPost
import retrofit2.Response

class changePasswordRepository {
    //suspend fun getPost(): Response<Post> {
    //    return RetrofitInstance.api.getPost()
    //}

    suspend fun pushPost(post : changePasswordPost): Response<changePasswordPost> {
        return changePasswordRetrofitInstance.api.pushPost(post)
    }
}