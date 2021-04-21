package com.example.navbar.ui.contact.contactRepository

import com.example.navbar.ui.contact.contactApi.contactRetrofitInstance
import com.example.navbar.ui.contact.contactModel.contactPost
import retrofit2.Response

class contactRepository {
    suspend fun pushPost(post : contactPost): Response<contactPost> {
        return contactRetrofitInstance.api.pushPost(post)
    }
}