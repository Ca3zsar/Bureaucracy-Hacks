package com.example.navbar.ui.institutions.PI.PIModel

class PIPost (
        val address: String,
        val phone: String,
        val latitude: Double,
        val name: String,
        val id: Int,
        val email: String,
        val url: String,
        val longitude: Double,
        val departments: List<Map<String, Any>>
)