package com.example.navbar.ui.institutions.CJASI.CjasiModel

class CjasiPost (
        val address: String,
        val phone: String,
        val name: String,
        val id: Int,
        val email: String,
        val url: String,
        val departments: List<Map<String, String>>
)