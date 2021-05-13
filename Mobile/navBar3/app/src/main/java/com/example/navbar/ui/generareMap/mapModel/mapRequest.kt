package com.example.navbar.ui.generareMap.mapModel

data class mapRequest(
    val latitude: String,
    val longitude: String,
    val institution: String,
    val necessary: List<String>
)