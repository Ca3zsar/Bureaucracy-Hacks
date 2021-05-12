package com.example.navbar.ui.generareMap.mapModel

data class mapResponse(
    val avoidVignette: List<String>,
    val currentLatitude: Double,
    val currentLongitude: Double,
    val institutions: List<Int>
)