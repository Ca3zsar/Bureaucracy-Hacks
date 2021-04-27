package com.example.navbar.ui.institutions.ANAF.AnafModel

import org.json.JSONArray
import org.json.JSONObject

data class AnafPost (
        val address: String,
        val phone: String,
        val name: String,
        val id: Int,
        val email: String,
        val url: String,
        val departments: List<Map<String, String>>
)